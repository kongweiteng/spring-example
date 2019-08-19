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

### VUE打包
```text
clean package -pl back-boot -am -Dmaven.test.skip=true
mvn clean package -pl back-boot -am docker:build -DpushImage --settings /settings.xml
mvn clean package docker:build -DpushImage --settings /settings.xml
# 构建测试环境
npm install --registry http://10.4.82.184:30850/repository/npm-group/ 
--registry https://registry.npm.taobao.org
npm run build:stage

# 构建生产环境
npm run build:prod
```

### KAFKA

- 1.kafka集群中zk的作用？
```text
1)Broker注册
　　Broker在zookeeper中保存为一个临时节点，节点的路径是/brokers/ids/[brokerid],每个节点会保存对应broker的IP以及端口等信息.

　　2)Topic注册

　　在kafka中,一个topic会被分成多个区并被分到多个broker上，分区的信息以及broker的分布情况都保存在zookeeper中，根节点路径为/brokers/topics,每个topic都会在topics下建立独立的子节点，每个topic节点下都会包含分区以及broker的对应信息，例如下图中的状态

　　3)生产者负载均衡

　　当Broker启动时，会注册该Broker的信息，以及可订阅的topic信息。生产者通过注册在Broker以及Topic上的watcher动态的感知Broker以及Topic的分区情况，从而将Topic的分区动态的分配到broker上.

　　4)消费者

　　kafka有消费者分组的概念，每个分组中可以包含多个消费者，每条消息只会发给分组中的一个消费者，且每个分组之间是相互独立互不影响的。

　　5)消费者与分区的对应关系

　　对于每个消费者分组，kafka都会为其分配一个全局唯一的Group ID,分组内的所有消费者会共享该ID,kafka还会为每个消费者分配一个consumer ID,通常采用hostname:uuid的形式。在kafka的设计中规定，对于topic的每个分区，最多只能被一个消费者进行消费，也就是消费者与分区的关系是一对多的关系。消费者与分区的关系也被存储在zookeeper中节点的路劲为 /consumers/[group_id]/owners/[topic]/[broker_id-partition_id],该节点的内容就是消费者的Consumer ID

　　6)消费者负载均衡

　　消费者服务启动时，会创建一个属于消费者节点的临时节点，节点的路径为 /consumers/[group_id]/ids/[consumer_id],该节点的内容是该消费者订阅的Topic信息。每个消费者会对/consumers/[group_id]/ids节点注册Watcher监听器，一旦消费者的数量增加或减少就会触发消费者的负载均衡。消费者还会对/brokers/ids/[brokerid]节点进行监听，如果发现服务器的Broker服务器列表发生变化，也会进行消费者的负载均衡

　　7)消费者的offset

　　在kafka的消费者API分为两种(1)High Level Api：由zookeeper维护消费者的offset (2) Low Level API,自己的代码实现对offset的维护。由于自己维护offset往往比较复杂，所以多数情况下都是使用High Level的APIoffset在zookeeper中的节点路径为/consumers/[group_id]/offsets/[topic]/[broker_id-part_id],该节点的值就是对应的offset
```

