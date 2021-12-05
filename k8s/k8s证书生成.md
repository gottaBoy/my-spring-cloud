### 方法一：通过openssh生成

```
vagrant ssh node1
sudo -i
cd /vagrant/addon/dashboard/
mkdir certs
openssl req -nodes -newkey rsa:2048 -keyout certs/dashboard.key -out certs/dashboard.csr -subj "/C=/ST=/L=/O=/OU=/CN=kubernetes-dashboard"
openssl x509 -req -sha256 -days 365 -in certs/dashboard.csr -signkey certs/dashboard.key -out certs/dashboard.crt
kubectl delete secret kubernetes-dashboard-certs -n kube-system
kubectl create secret generic kubernetes-dashboard-certs --from-file=certs -n kube-system
kubectl delete pods $(kubectl get pods -n kube-system|grep kubernetes-dashboard|awk '{print $1}') -n kube-system #重新创建dashboard

```

### 方法二：CloudFlare
生成的 CA 证书和秘钥文件如下：

ca-key.pem
ca.pem
kubernetes-key.pem
kubernetes.pem
kube-proxy.pem
kube-proxy-key.pem
admin.pem
admin-key.pem
使用证书的组件如下：

etcd：使用 ca.pem、kubernetes-key.pem、kubernetes.pem；
kube-apiserver：使用 ca.pem、kubernetes-key.pem、kubernetes.pem；
kubelet：使用 ca.pem；
kube-proxy：使用 ca.pem、kube-proxy-key.pem、kube-proxy.pem；
kubectl：使用 ca.pem、admin-key.pem、admin.pem；
kube-controller-manager：使用 ca-key.pem、ca.pem

#### 安装 CFSSL
方式一：直接使用二进制源码包安装
```
wget https://pkg.cfssl.org/R1.2/cfssl_linux-amd64
chmod +x cfssl_linux-amd64
mv cfssl_linux-amd64 /usr/local/bin/cfssl

wget https://pkg.cfssl.org/R1.2/cfssljson_linux-amd64
chmod +x cfssljson_linux-amd64
mv cfssljson_linux-amd64 /usr/local/bin/cfssljson

wget https://pkg.cfssl.org/R1.2/cfssl-certinfo_linux-amd64
chmod +x cfssl-certinfo_linux-amd64
mv cfssl-certinfo_linux-amd64 /usr/local/bin/cfssl-certinfo

export PATH=/usr/local/bin:$PATH
```
方式二：使用go命令安装
```
go get -u github.com/cloudflare/cfssl/cmd/...
echo $GOPATH
/usr/local
$ls /usr/local/bin/cfssl*
cfssl cfssl-bundle cfssl-certinfo cfssljson cfssl-newkey cfssl-scan
```
创建 CA (Certificate Authority)
#### 创建 CA 配置文件
/Users/minyi/go/bin/cfssl*
/Users/minyi/go/bin/cfssl print-defaults config > config.json
```
mkdir /root/ssl
cd /root/ssl
cfssl print-defaults config > config.json
cfssl print-defaults csr > csr.json
# 根据config.json文件的格式创建如下的ca-config.json文件
# 过期时间设置成了 87600h
cat > ca-config.json <<EOF
{
  "signing": {
    "default": {
      "expiry": "87600h"
    },
    "profiles": {
      "kubernetes": {
        "usages": [
            "signing",
            "key encipherment",
            "server auth",
            "client auth"
        ],
        "expiry": "87600h"
      }
    }
  }
}
EOF
```
字段说明

ca-config.json：可以定义多个 profiles，分别指定不同的过期时间、使用场景等参数；后续在签名证书时使用某个 profile；
signing：表示该证书可用于签名其它证书；生成的 ca.pem 证书中 CA=TRUE；
server auth：表示client可以用该 CA 对server提供的证书进行验证；
client auth：表示server可以用该CA对client提供的证书进行验证；

#### 创建 CA 证书签名请求

创建 ca-csr.json 文件，内容如下：
```
{
  "CN": "kubernetes",
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "ShangHai",
      "L": "ShangHai",
      "O": "k8s",
      "OU": "System"
    }
  ],
    "ca": {
       "expiry": "87600h"
    }
}
```

"CN"：Common Name，kube-apiserver 从证书中提取该字段作为请求的用户名 (User Name)；浏览器使用该字段验证网站是否合法；
"O"：Organization，kube-apiserver 从证书中提取该字段作为请求用户所属的组 (Group)；
生成 CA 证书和私钥
```
cfssl gencert -initca ca-csr.json | cfssljson -bare ca
ls ca*
ca-config.json  ca.csr  ca-csr.json  ca-key.pem  ca.pem
```

#### 创建 kubernetes 证书
创建 kubernetes 证书签名请求文件 kubernetes-csr.json：
```
{
    "CN": "kubernetes",
    "hosts": [
      "127.0.0.1",
      "192.168.56.101",
      "192.168.56.102",
      "192.168.56.103",
      "192.168.56.104",
      "10.254.0.1",
      "kubernetes",
      "kubernetes.default",
      "kubernetes.default.svc",
      "kubernetes.default.svc.cluster",
      "kubernetes.default.svc.cluster.local"
    ],
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "ST": "ShangHai",
            "L": "ShangHai",
            "O": "k8s",
            "OU": "System"
        }
    ]
}
```
如果 hosts 字段不为空则需要指定授权使用该证书的 IP 或域名列表，由于该证书后续被 etcd 集群和 kubernetes master 集群使用，所以上面分别指定了 etcd 集群、kubernetes master 集群的主机 IP 和 kubernetes 服务的服务 IP（一般是 kube-apiserver 指定的 service-cluster-ip-range 网段的第一个IP，如 10.254.0.1）。
这是最小化安装的kubernetes集群，包括一个私有镜像仓库，三个节点的kubernetes集群，以上物理节点的IP也可以更换为主机名。

生成 kubernetes 证书和私钥
```
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes kubernetes-csr.json | cfssljson -bare kubernetes
ls kubernetes*
kubernetes.csr  kubernetes-csr.json  kubernetes-key.pem  kubernetes.pem
```

或者直接在命令行上指定相关参数：
```
echo '{"CN":"kubernetes","hosts":[""],"key":{"algo":"rsa","size":2048}}' | cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes -hostname="127.0.0.1,192.168.56.101,192.168.56.102,192.168.56.103,192.168.56.104,kubernetes,kubernetes.default" - | cfssljson -bare kubernetes
```

#### 创建 admin 证书
创建 admin 证书签名请求文件 admin-csr.json：
```
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes admin-csr.json | cfssljson -bare admin

{
  "CN": "admin",
  "hosts": [],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "ShangHai",
      "L": "ShangHai",
      "O": "system:masters",
      "OU": "System"
    }
  ]
}
```
后续 kube-apiserver 使用 RBAC 对客户端(如 kubelet、kube-proxy、Pod)请求进行授权；
kube-apiserver 预定义了一些 RBAC 使用的 RoleBindings，如 cluster-admin 将 Group system:masters 与 Role cluster-admin 绑定，该 Role 授予了调用kube-apiserver 的所有 API的权限；
O 指定该证书的 Group 为 system:masters，kubelet 使用该证书访问 kube-apiserver 时 ，由于证书被 CA 签名，所以认证通过，同时由于证书用户组为经过预授权的 system:masters，所以被授予访问所有 API 的权限；
注意：这个admin 证书，是将来生成管理员用的kube config 配置文件用的，现在我们一般建议使用RBAC 来对kubernetes 进行角色权限控制， kubernetes 将证书中的CN 字段 作为User， O 字段作为 Group（具体参考 Kubernetes中的用户与身份认证授权中 X509 Client Certs 一段）。

在搭建完 kubernetes 集群后，我们可以通过命令: kubectl get clusterrolebinding cluster-admin -o yaml ,查看到 clusterrolebinding cluster-admin 的 subjects 的 kind 是 Group，name 是 system:masters。 roleRef 对象是 ClusterRole cluster-admin。 意思是凡是 system:masters Group 的 user 或者 serviceAccount 都拥有 cluster-admin 的角色。 因此我们在使用 kubectl 命令时候，才拥有整个集群的管理权限。
kubectl get clusterrolebinding cluster-admin -o yaml
生成 admin 证书和私钥：
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes admin-csr.json | cfssljson -bare admin
ls admin*
admin.csr  admin-csr.json  admin-key.pem  admin.pem

#### 创建 kube-proxy 证书
创建 kube-proxy 证书签名请求文件 kube-proxy-csr.json：
```
{
  "CN": "system:kube-proxy",
  "hosts": [],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "ShangHai",
      "L": "ShangHai",
      "O": "k8s",
      "OU": "System"
    }
  ]
}
```

CN 指定该证书的 User 为 system:kube-proxy；
kube-apiserver 预定义的 RoleBinding system:node-proxier 将User system:kube-proxy 与 Role system:node-proxier 绑定，该 Role 授予了调用 kube-apiserver Proxy 相关 API 的权限；
生成 kube-proxy 客户端证书和私钥
```
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes  kube-proxy-csr.json | cfssljson -bare kube-proxy
ls kube-proxy*
kube-proxy.csr  kube-proxy-csr.json  kube-proxy-key.pem  kube-proxy.pem
```

#### 创建 kube-scheduler 证书
创建 kube-scheduler 证书签名请求文件 kube-scheduler-csr.json：
```
{
    "CN": "system:kube-scheduler",
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "ST": "ShangHai",
            "L": "ShangHai",
            "O": "system:kube-scheduler",
            "OU": "System"
        }
    ]
}
```
CN 指定该证书的 User 为 system:kube-scheduler；
kube-apiserver 预定义的 RoleBinding system:node-proxier 将User system:kube-scheduler 与 Role system:node-proxier 绑定，该 Role 授予了调用 kube-apiserver Proxy 相关 API 的权限；
生成 kube-scheduler 客户端证书和私钥
```
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes  kube-scheduler-csr.json | cfssljson -bare scheduler
ls scheduler*
scheduler.csr  scheduler-csr.json  scheduler-key.pem  scheduler.pem
```


#### pem -> p12  or p12 -> pem
cer.p12:　　　　openssl pkcs12 -clcerts -nokeys -out cer.pem -in cer.p12
key.p12:　　　　openssl pkcs12 -nocerts -out key.pem -in key.p12
openssl pkcs12 -export -in admin.pem -out admin.p12

#### 校验证书
以 Kubernetes 证书为例。

使用 openssl 命令
```
openssl x509  -noout -text -in  kubernetes.pem
```
确认 Issuer 字段的内容和 ca-csr.json 一致；
确认 Subject 字段的内容和 kubernetes-csr.json 一致；
确认 X509v3 Subject Alternative Name 字段的内容和 kubernetes-csr.json 一致；
确认 X509v3 Key Usage、Extended Key Usage 字段的内容和 ca-config.json 中 kubernetes profile 一致；

使用 cfssl-certinfo 命令
cfssl-certinfo -cert kubernetes.pem

分发证书
将生成的证书和秘钥文件（后缀名为.pem）拷贝到所有机器的 /etc/kubernetes/ssl 目录下备用；
```
mkdir -p /etc/kubernetes/ssl
cp *.pem /etc/kubernetes/ssl
```
