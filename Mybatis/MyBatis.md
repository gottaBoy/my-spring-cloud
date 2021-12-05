# mybatis深入理解与实践


## Mybatis 快速入门 (第一天)


#### ORM 简介


#### 常见持久化框架


#### Mybatis 实例


#### Mybatis 整体架构


##### 基础支持层


##### 核心处理层


##### 接口层


## 基础支持层 (第一天)


### 解析器模块


#### XPath 简介


#### XPathParser
- GenericTokenParser
提取表达式 start end offset expression
- PropertyParser
默认实现变量处理器
- TokenHandler
- VariableTokenHandler
- XNode
- XPathParser
commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver)
String xml
Reader reader
InputStream inputStream
Document document

### 反射工具箱


#### Reflector&ReflectorFactory


#### TypeParameterResolver


#### ObjectFactory


#### Property 工具集


#### MetaClass


#### ObjectWrapper


#### MetaObject


### 类型转换


#### TypeHandlerRegistry


#### TypeAliasRegistry


### 日志模块


#### 适配器模式


#### 日志适配器


#### 代理模式与JDK动态代理


#### JDBC调试


### 资源加载


#### 类加载器简介


#### ClassLoaderWrapper


#### ResolverUtil


#### 单例模式


#### VFS


### DataSource


#### 工厂方法模式


#### DataSourceFactory


#### UnpoolDataSource


### Transaction


### bind 模块


#### MapperRegistry&MapperProxyFactory


#### MapperProxy


#### MapperMethod


### 缓存模块


#### 装饰器模式


#### Cache 接口及其实现


#### CacheKey
