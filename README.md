# idmesh-servlet-sample

常规web应用的样例，使用servlet实现。

## 0.前提条件
在控制台配置应用的回调地址
```
http://localhost:8080/callback
```
获取应用domain、client_id和client_secret。[去获取](https://idaas.idmesh.site/console)

## 1.配置应用
在src/main/webapp/WEB-INF/web.xml中配置应用的参数。
```
    <context-param>
        <param-name>com.idmesh.domain</param-name>
        <param-value>${domain}</param-value>
    </context-param>
    <context-param>
        <param-name>com.idmesh.clientId</param-name>
        <param-value>${client_id}</param-value>
    </context-param>
    <context-param>
        <param-name>com.idmesh.clientSecret</param-name>
        <param-value>${clientSecret}</param-value>
    </context-param>
```

## 2.运行

```bash
./gradlew appRun
```

## 3.访问测试

```
打开浏览器输入：http://localhost:8080 登录应用
```

