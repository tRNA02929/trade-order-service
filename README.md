# trade-order

## 说明

* 关于缓存：
    - 本项目中user信息使用caffeine作为一级缓存，region信息则在初始化是一次性读取到内存中，除此之外，其他结构使用mybatis作为持久层框架，并且启用了mybatis的二级缓存。
* 关于负载均衡：
    - 本项目使用的为轮询算法。
* 关于redis实现漏桶算法：
    - 本项目使用的为lua脚本实现的漏桶算法，通过redis中的两个key（libo14:listUpstreamInfo:water，libo14:listUpstreamInfo:lastTime）获取水量和时间戳，并进行计算。 


* 项目编码：UTF-8
* JDK:1.8
* IDE：不限
* 项目开发方式
    - 分支开发，发布时合并主干
    - 环境隔离（profile：local、dev、uat、prod）

## 模块

* biz：公共业务模块（供其它模块调用）<br/>
* biz-base:基础业务 <br/>
* biz-req-trace:请求链路跟踪<br/>
* biz-glue:<br/>
* trade-order-api：业务代码 <br/>
* trade-order-gateway：路由服务 <br/>

