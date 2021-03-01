<h1 style="text-align: center">RatelBootFramework</h1>

## 主要特性

- 使用最新技术栈，社区资源丰富。

## 系统功能

- 邮件工具：配合富文本，发送html格式的邮件

## 项目结构

项目采用按功能分模块的开发方式，结构如下

- `ratel-boot` 为系项目入口模块

- `ratel-core` 为系统的核心模块，各种工具类，公共配置存在该模块

- `ratel-modules` 为系统功能模块

### 1、工具模块 `ratel-tools`

- 邮件工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-tools</artifactId>
</dependency>
```

### 2、日志模块 `ratel-log`

- 日志工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-log</artifactId>
</dependency>
```

### 3、系统模块 `ratel-system`

- 用户管理
- 机构管理
- 资源管理
- 角色管理
- 字典管理

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-system</artifactId>
</dependency>
```

### 4、权限模块 `ratel-security`

- 权限工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-security</artifactId>
</dependency>
```

### 5、文件模块 `ratel-file`

- 文件工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-file</artifactId>
</dependency>
```

### 6、接口文档工具 `ratel-common-swagger`

- swagger工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-common-swagger</artifactId>
</dependency>
```

### 7、redis工具 `ratel-common-redis`

- redis工具

```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-common-redis</artifactId>
</dependency>
```

```
1、在 resources/context/ratel-spring-redis.xml 中配置

<bean id="ratelTemplateConfig" class="org.ratel.framework.redis.template.RatelTemplateConfig" init-method="init"
      p:dbIndex="0"
      p:hostName="127.0.0.1"
      p:port="6379"
      p:password=""
      p:timeout="3000"
      p:maxActive="8" p:maxIdle="8" p:minIdle="0" p:maxWait="-1"
      p:shutdownTimeout="4000"/>


<bean id="ratelLogRedisTemplate" class="org.ratel.framework.redis.template.RatelRedisTemplate">
    <constructor-arg name="ratelTemplateConfig" ref="ratelTemplateConfig"/>
</bean>

2、使用 ratelLogRedisTemplate 

@Autowired
@Qualifier("ratelLogRedisTemplate")
private RatelRedisTemplate strRedisTemplate;

2、调用Template方法 

strRedisTemplate.set(user);
UserEntity user2 = (UserEntity) strRedisTemplate.get(user.getKey());
......
```

### 8、线程工具 `ratel-common-thread`

- 线程工具
```
<dependency>
    <groupId>org.ratel</groupId>
    <artifactId>ratel-common-thread</artifactId>
</dependency>
```