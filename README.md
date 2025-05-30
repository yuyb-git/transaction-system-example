# 银行交易模拟系统

## 简介
模拟银行交易系统

## 技术栈
- 后端：Spring Boot、Spring Web、Spring Cache、caffeine
- 前端：Html、Vue
- 测试：JUnit、Spring Boot Test

## 依赖库
- `spring-boot-starter-web`：对外提供API。
- `spring-boot-starter-validation`：参数校验。
- `spring-boot-starter-cache`：使用缓存。
- `caffeine`：使用caffeine高性能缓存替代默认的cache。
- `org.projectlombok`：提供数据注解。
- `pagehelper-spring-boot-starter`：分页。
- `spring-boot-starter-test`：用于编写和运行单元测试。
- `spring-boot-starter-actuator`：运行时监控。

## 运行项目

## 本地运行

##### 1. 克隆项目git clone https://github.com/yuyb-git/transaction-system-example
```
cd transaction-management
```
##### 2. 构建项目
```
mvn clean package
```
##### 3. 运行项目
```
java -jar target/transaction-system.jar
```

## 容器运行
### 1. 构建 Docker 镜像
```
mvn compile jib:build
```

### 2. 运行 Docker 容器
```
docker run example/transaction-system:0.1
```

## 访问网站
打开浏览器，输入如下url即可打开交易记录管理页面。

```
http://localhost:8080/
```
