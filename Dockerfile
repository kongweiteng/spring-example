FROM openjdk:8-jdk-slim
# 切换到 /opt目录
WORKDIR /opt

RUN ls /

# 将编译的jar拷贝的当前目录
ADD /spring-example/back-boot/target/back-boot-0.0.1-SNAPSHOT.jar .
# 配置启动命令(两种方式，一种是写死在dockerfile，一种是在rancher上配置，相比前者，后者更灵活)
#CMD java -Dlog.kafka.address=$kafka_address -Dlog.kafka.topic=$kafka_topic -Ddisconf.conf_server_host=$conf_server -Ddisconf.env=$Env  -jar /opt/webapps/$jar_file
#CMD java -server -DlogRedisAddress=192.168.1.97 -Dpinpoint.applicationName=baseapi -Dspring.cloud.consul.enabled=true -Dspring.profiles.active=test -Xms512m -Xmx512m -jar /opt/apps/baseapi.jar
