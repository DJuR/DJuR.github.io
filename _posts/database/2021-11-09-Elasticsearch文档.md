---
title: Elasticsearch 文档
author: DJuR
date: 2021-11-09
tags: SQL
category: Database
layout: book
---

{:toc}

# Elasticsearch 7.15

​	Elasticsearch分布式搜索和分析引擎是Elastic Stack的核心。Logstash和Beats有助于收集、聚合和丰富数据，并将其存储在Elasticsearch中。Kibana能够交互式地探索、可视化和共享数据，并管理和监控堆栈。Elasticsearch可以索引、搜索和分析数据。

​	Elasticsearch为所有类型的数据提供**近乎实时**的搜索和分析。无论是结构化或非结构化文本、数字数据还是地理空间数据，Elasticsearch都能以快速搜索的方式有效地存储和索引它们。可以数据检索和聚合信息。随着数据和查询量的增长，Elasticsearch的分布式特性可以使的部署能够与之无缝地增长。

## 设置Elasticsearch

本节包括一下内容

* 下载
* 安装
* 配置
* 使用

### 安装Elasticsearch

[参考官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/7.15/install-elasticsearch.html)

### 配置Elasticsearch

​	Elasticsearch拥有良好的默认设置，并且只需要很少的配置。可以使用集群更新设置API在正在运行的集群上更改大多数设置。

#### 配置文件位置

* `elasticsearch.yml `ES配置
* `jvm.options`ES`JVM`设置
* `log4j2.propetries`ES日志配置

#### 环境变量配置

​	ES配置项可以使用`${..}`，被环境变量的值替换

* 示例：

  ```yaml
  node.name:    ${HOSTNAME}
  network.host: ${ES_NETWORK_HOST}
  ```

​	环境变量

```
export HOSTNAME="MY_host_name"
```

### 启动ES

#### 从命令行运行Elasticsearch

```shell
./bin/elasticsearch
```

#### 守护进程运行Elasticsearch

`-d`设置守护进程运行，`-p`设置进程pid文件

```
./bin/elasticsearch -d -p pid
```

结束ES，杀死pid文件中记录的pid进程：

```
pkill -F pid
```

### 停止ES

​	有序关闭ES可以确保ES有机会清理和关闭未使用的资源。例如，以有序方式关闭的节点将自己从集群中删除，将translogs同步到磁盘，并执行其他相关的清理活动。通过正确地停止Elasticsearch，可以帮助确保有序关闭。

* 查看`ES` pid

```
jps | grep Elasticsearch
```

* 停止`ES`

```
kill -SIGTERM pid
```

## 索引模块

索引设置：

* 静态

  在索引创建时或关闭的索引(打开关闭的索引`POST /myindex/_open`)。

* 动态

  使用API动态更改设置。

  示例：

  ```console
  PUT /my_index/_settings
  {
    "index" : {
      "number_of_replicas" : 2
    }
  }
  ```

#### 静态索引配置

静态索引配置列表（非特定索引）:

* index.number_of_shards

  索引的主分片数，默认1。该设置只能在创建索引是设置。

  📢 每个索引的分片数量被限制在1024。

* index.number_of_routing_shards

  与`index.number_of_shards`一起使用，路由文档到主分片。

* index.codec

  设置压缩数据格式，默认LZ4。可以设置`best_compression`更高的压缩比，但是存储性能降低。更新压缩类型，新的压缩类型将在段合并后应用，也可以强制合并。

#### 动态索引配置



## 索引模板

​	索引模板是在创建索引是告诉ES如何配置索引，对与数据流，索引在写入数据是创建。模板在创建索引前设置，当创建索引时，模板将作为创建索引的基础。

​	有两种模板：索引模板和组件模板。组件模板是可重用的构建块，用于配置mapping、setting和aliases。虽然可以使用组件模板构造索引模板，但是他们不能直接应用于一组索引。索引模板可以包含组件模板的集合，也可以直接配置mapping、setting和aliases。

