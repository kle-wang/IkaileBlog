# 凯乐窝

## 初步开发完毕 本仓库目的在于记录自己的学习记录

### 项目介绍

```
技术栈：Springboot Mybatis-plus Mysql Redis
```

### 开发环境

```
系统：MacOs Monterey
Idea:IntelliJ IDEA 2022.1 (Ultimate Edition)
     Build #IU-221.5080.210, built on April 12, 2022
```

### 介绍

目前功能还比较简陋

    1.注册、登录功能
    2.分页查询文章
    3.根据分类查询文章
    4.发表、删除文章

### application.yml

```xml
spring:
        datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: username
        password: password@
        url: jdbc:mysql://10.211.55.3:3306/blog
        redis:
        host: 10.211.55.3
        port: 6379
        password: password
        database: 0
        mail:
        host: host
        username: username
        password: password
        mvc:
        pathmatch:
        matching-strategy: ant_path_matcher
        mybatis:
        configuration:
        cache-enabled: true
        mybatis-plus:
        configuration:
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        map-underscore-to-camel-case: false
```

### 数据库自行根据代码创