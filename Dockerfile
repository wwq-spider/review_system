# 使用官方 maven/Java 8 镜像作为构建环境
FROM maven:3.6-jdk-8 as builder
# 将代码复制到容器内
WORKDIR /app
COPY pom.xml .
COPY src ./src
# 构建项目
RUN mvn package -DskipTests
#解决时区不一致问题
FROM centos as centos

FROM tomcat:8-jre8
MAINTAINER "errorlife <steffan.fan@foxmail.com>"
COPY --from=centos  /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
#将war包添加进webapps中 --from参数表示从上面构建的镜像builder中拷贝war包
COPY --from=builder /app/target/review_system.war ./webapps/
CMD ["catalina.sh", "run"]

#FROM tomcat:8-jre8
##WORKDIR /app
#MAINTAINER "errorlife <steffan.fan@foxmail.com>"
##将war包添加进webapps中
#COPY target/review_system.war ./webapps/
#CMD ["catalina.sh", "run"]