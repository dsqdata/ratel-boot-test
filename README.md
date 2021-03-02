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
```
1、客户端密码加密
   登陆接口接收RSA加密的密码
   
   JS:
    import { JSEncrypt } from 'jsencrypt'

    // 密钥对生成 http://web.chacuo.net/netrsakeypair
    const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD\n' +
      '2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ=='
    
    const privateKey = 'MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0vfvyTdGJkdbHkB8\n' +
      'mp0f3FE0GYP3AYPaJF7jUd1M0XxFSE2ceK3k2kw20YvQ09NJKk+OMjWQl9WitG9p\n' +
      'B6tSCQIDAQABAkA2SimBrWC2/wvauBuYqjCFwLvYiRYqZKThUS3MZlebXJiLB+Ue\n' +
      '/gUifAAKIg1avttUZsHBHrop4qfJCwAI0+YRAiEA+W3NK/RaXtnRqmoUUkb59zsZ\n' +
      'UBLpvZgQPfj1MhyHDz0CIQDYhsAhPJ3mgS64NbUZmGWuuNKp5coY2GIj/zYDMJp6\n' +
      'vQIgUueLFXv/eZ1ekgz2Oi67MNCk5jeTF2BurZqNLR3MSmUCIFT3Q6uHMtsB9Eha\n' +
      '4u7hS31tj1UWE+D+ADzp59MGnoftAiBeHT7gDMuqeJHPL4b+kC+gzV4FGTfhR9q3\n' +
      'tTbklZkD2A=='
    
    // 加密
    export function encrypt(txt) {
      const encryptor = new JSEncrypt()
      encryptor.setPublicKey(publicKey) // 设置公钥
      return encryptor.encrypt(txt) // 对需要加密的数据进行加密
    }
    
    // 解密
    export function decrypt(txt) {
      const encryptor = new JSEncrypt()
      encryptor.setPrivateKey(privateKey)
      return encryptor.decrypt(txt)
    }

   JAVA:
    public static void main(String[] args) throws Exception {
        //加密字符串
        String message = "df723820";
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ==";
        String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0vfvyTdGJkdbHkB8mp0f3FE0GYP3AYPaJF7jUd1M0XxFSE2ceK3k2kw20YvQ09NJKk+OMjWQl9WitG9pB6tSCQIDAQABAkA2SimBrWC2/wvauBuYqjCFwLvYiRYqZKThUS3MZlebXJiLB+Ue/gUifAAKIg1avttUZsHBHrop4qfJCwAI0+YRAiEA+W3NK/RaXtnRqmoUUkb59zsZUBLpvZgQPfj1MhyHDz0CIQDYhsAhPJ3mgS64NbUZmGWuuNKp5coY2GIj/zYDMJp6vQIgUueLFXv/eZ1ekgz2Oi67MNCk5jeTF2BurZqNLR3MSmUCIFT3Q6uHMtsB9Eha4u7hS31tj1UWE+D+ADzp59MGnoftAiBeHT7gDMuqeJHPL4b+kC+gzV4FGTfhR9q3tTbklZkD2A==";
        String messageEn = encrypt(message,publicKey);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, privateKey);
        System.out.println("还原后的字符串为:" + messageDe);
    }
    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
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