
# **功能介绍**
### 服务管理
- 服务上下线
- 节点添加/删除
- 服务查询
- 服务节点查询

### 服务治理
- 限流
- 降级

### 服务监控
- QPS、AvgTime、P999
- 服务依赖拓扑图
- Grafana等监控系统
- 调用链

### 问题定位
- 服务监控来发觉异常
- 定位一次用户请求失败

### 日志查询
- ELK的日志系统
- 用户请求的数据统计


基于layui+springcloud的企业级微服务框架(用户权限管理，配置中心管理，应用管理，....),其核心的设计目标是分离前后端，快速开发部署，学习简单，功能强大，提供快速接入核心接口能力，其目标是帮助企业搭建一套类似百度能力开放平台的框架；
- 基于layui前后端分离的企业级微服务架构
- 兼容spring cloud netflix & spring cloud alibaba
- 优化Spring Security内部实现，实现API调用的统一出口和权限认证授权中心
- 提供完善的企业微服务流量监控，日志监控能力
- 通用的微服务架构应用非功能性(NFR)需求,更容易地在不同的项目中复用
- 提供完善的压力测试方案
- 提供完善的灰度发布方案
- 提供完善的微服务部署方案

- 统一安全认证中心
	- 支持oauth的四种模式登录
	- 支持用户名、密码加图形验证码登录
	- 手机校验码登录
	- 支持第三方系统单点登录
- 微服务架构基础支撑
	- 服务注册发现、路由与负载均衡
	- 服务熔断与限流
	- 统一配置中心
	- 统一日志中心
	- 分布式锁
	- 分布式任务调度器
- 系统服务监控中心
	- 服务调用链监控
	- 应用吞吐量监控
	- 服务降级、熔断监控
	- 微服务服务监控
- 能力开放平台业务支撑
	- 网关基于应用方式API接口隔离
	- 网关基于应用限制调用次数
	- 下游服务基于RBAC权限管理，实现细粒度控制
	- 代码生成器中心
	- 网关聚合服务内部Swagger接口文档
	- 统一跨域处理
	- 统一异常处理
- docker容器化部署
	- 基于rancher的容器化部署
	- 基于docker的elk日志监控
	- 基于docker的服务动态扩容 
	
### 数据库相关
- [mysql](https://www.cnblogs.com/chenmh/p/5821844.html)
- [mysql慢查询](https://www.cnblogs.com/chenmh/p/5014077.html)
- [sysbench 压力测试](https://www.cnblogs.com/chenmh/p/5866058.html)

- [api-doc](http://127.0.0.1:8000/api-auth/doc.html)
- [learning_resource](https://github.com/CoderMerlin/coder-programming/blob/master/src/17-other/learning_resource.md)
- [springcloud](https://www.bilibili.com/video/av22613028?t=35)
- [OAUTH](https://www.bilibili.com/video/av50683258?from=search&seid=11420134487278875473)
- [redis](https://www.bilibili.com/video/av49517046?from=search&seid=5265281439863297247)
- [elk](https://www.bilibili.com/video/av35467284?from=search&seid=1694755319241225491)
- [Docker on Mac](https://yeasy.gitbook.io/docker_practice/install/mac)
- [Centos](http://github.com/CommanderK5)
- [dockerfile](http://static.kancloud.cn/noahs/linux/1532838)
- [docker](https://www.itmuch.com/docker/12-docker-maven/)






