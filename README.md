# spring-example
```text
spring boot开发脚手架，后端采用spring boot 2.x，前端采用vue
前端项目支持两种部署方式，1.将vue项目build 后将dist文件copy到nginx下，访问项目。2.将vue项目build 后将dist文件copy到spring boot项目的resource/static下，启动spring boot项目访问根目录即可

```


## redis
```text
实现spring boot集成redis 并且实现对redis操作工具类
实现redis管理session ，使用httpsesion进行操作即可实现

```

## ldap
```text
实现ldap协议的登录方式
```

## rabbitmq
```text
实现rabbitmq交换机、队列的创建、绑定
实现对交换机、队列生成数据
实现消费数据
安装插件 （安装命令：rabbitmq-plugins enable rabbitmq_web_stomp ）实现web 页面通过socket消费消息


```


## elastic search 
```xml
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
    <version>6.5.4</version>
 </dependency>
 
```

```text
利用es官方网站推荐的高级java客户端

服务端版本是7.0.1

推荐文档：https://es.quanke.name

```

### 打包
```text
clean package -pl back-boot -am -Dmaven.test.skip=true
mvn clean package -pl back-boot -am docker:build -DpushImage
mvn clean package docker:build -DpushImage
# 构建测试环境
npm install --registry http://10.4.82.184:30850/repository/npm-group/ 

npm run build:stage

# 构建生产环境
npm run build:prod
```
