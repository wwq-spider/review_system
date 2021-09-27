# 使用官方 maven/Java 8 镜像作为构建环境
FROM maven:3.6-jdk-8 as builder
# 将代码复制到容器内
WORKDIR /app
COPY pom.xml .
COPY src ./src
# 构建项目
RUN mvn package -DskipTests

FROM tomcat:8-jre8
MAINTAINER "errorlife <steffan.fan@foxmail.com>"
ADD /app/target/review_system.war /usr/local/tomcat/webapps/ #将war包添加进webapps中
CMD ["catalina.sh", "run"]