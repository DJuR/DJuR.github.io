---
title: Elasticsearch 笔记
author: DJuR
date: 2021-11-06
tags: SQL
category: Database
layout: book
---

* content
  
  {:toc}

> Elasticsearch 学习笔记。

<!--more-->



# 基础入门

> PUT，DELETE操作是幂等的，既不管进行多少次操作，结果都一样。



## 基本操作

### 索引 - 创建

- 示例：

  ```http
  PUT http://localhost:9200/userinfo
  ```

  结果：

  ```
  {
      "acknowledged": true,
      "shards_acknowledged": true,
      "index": "userinfo"
  }
  ```

  

### 索引 - 获取单个

- 示例：

  ```http
  GET http://localhost:9200/userinfo
  ```

  结果:

  ```json
  {
      "userinfo": {
          "aliases": {},
          "mappings": {},
          "settings": {
              "index": {
                  "routing": {
                      "allocation": {
                          "include": {
                              "_tier_preference": "data_content"
                          }
                      }
                  },
                  "number_of_shards": "1",
                  "provided_name": "userinfo",
                  "creation_date": "1636252249634",
                  "number_of_replicas": "1",
                  "uuid": "tHZiVh5tSvKQhmeU9vrRQA",
                  "version": {
                      "created": "7150199"
                  }
              }
          }
      }
  }
  ```

### 索引 - 获取所用

- 示例：

  ```http
  GET http://localhost:9200/_cat/indices?v
  ```

  结果：

  ```reStructuredText
  health status index    uuid                   pri rep docs.count docs.deleted store.size pri.store.size
  yellow open   shoping  bZOtr1tmQlGXt9Lmgm2yrw   1   1          1            0     21.5kb         21.5kb
  yellow open   userinfo tHZiVh5tSvKQhmeU9vrRQA   1   1          0            0       208b           208b
  ```

### 索引 - 删除

* 示例：

  ```http
  DELETE http://localhost:9200/userinfo
  ```

  结果:

  ```json
  {
      "acknowledged": true
  }
  ```

### 文档 - 创建 PUT

* 示例：

  ```http
  PUT http://localhost:9200/userinfo/_doc/1001
  {
      "name":"DJuR",
      "age":29,
      "sex":"男的"
  }
  ```

  结果：

  ```json
  {
      "_index": "shoping",
      "_type": "_doc",
      "_id": "1001",
      "_version": 1,
      "result": "created",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 15,
      "_primary_term": 1
  }
  ```

### 文档 - 创建 POST

> POST 是非幂等的，多次请求创建多个文档
>
> POST 创建可添加主键id`http://localhost:9200/userinfo/_doc/1002`

* 示例：

  ```http
  POST http://localhost:9200/userinfo/_doc
  {
      "name":"张三",
      "age":40,
      "sex":"女的"
  }
  ```

  结果：

  ```json
  {
      "_index": "userinfo",
      "_type": "_doc",
      "_id": "1002",
      "_version": 1,
      "result": "created",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 5,
      "_primary_term": 1
  }
  ```

### 文档 - 全量查询

* 示例：

  ```http
  GET http://localhost:9200/userinfo/_search
  ```

  结果：

  ```json
  {
      "took": 424,
      "timed_out": false,
      "_shards": {
          "total": 1,
          "successful": 1,
          "skipped": 0,
          "failed": 0
      },
      "hits": {
          "total": {
              "value": 2,
              "relation": "eq"
          },
          "max_score": 1.0,
          "hits": [
              {
                  "_index": "userinfo",
                  "_type": "_doc",
                  "_id": "1001",
                  "_score": 1.0,
                  "_source": {
                      "name": "张三",
                      "age": 40,
                      "sex": "女的"
                  }
              },
              {
                  "_index": "userinfo",
                  "_type": "_doc",
                  "_id": "1002",
                  "_score": 1.0,
                  "_source": {
                      "name": "张三",
                      "age": 40,
                      "sex": "女的"
                  }
              }
          ]
      }
  }
  ```

### 文档 - 主键查询

* 示例：

  ```http
  GET http://localhost:9200/userinfo/_doc/1001
  ```

  结果：

  ```json
  {
      "_index": "userinfo",
      "_type": "_doc",
      "_id": "1001",
      "_version": 5,
      "_seq_no": 4,
      "_primary_term": 1,
      "found": true,
      "_source": {
          "name": "张三",
          "age": 40,
          "sex": "女的"
      }
  }
  ```

### 文档 - 全量修改

> 该操作相当于**先删除**文档，**然后创建**文档

* 示例：

  ```http
  PUT http://localhost:9200/userinfo/_doc/1001
  {
      "name": "张三",
      "age": 30,
      "sex": "男的"
  }
  ```

  结果：

  ```json
  {
      "_index": "userinfo",
      "_type": "_doc",
      "_id": "1001",
      "_version": 6,
      "result": "updated",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 6,
      "_primary_term": 1
  }
  ```

### 文档 - 局部修改

> 只修改指定的字段

* 示例：

  ```http
  POST http://localhost:9200/userinfo/_update/1001
  {
     "doc":{
         "name":"王二麻子"
     }
  }
  ```

  结果：

  ```json
  {
      "_index": "userinfo",
      "_type": "_doc",
      "_id": "1002",
      "_version": 11,
      "result": "updated",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 18,
      "_primary_term": 1
  }
  ```

### 文档 - 删除

* 示例：

  ```http
  DELETE http://localhost:9200/userinfo/_doc/1001
  ```

  结果：

  ```json
  {
      "_index": "userinfo",
      "_type": "_doc",
      "_id": "1001",
      "_version": 8,
      "result": "deleted",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 19,
      "_primary_term": 1
  }
  ```

# 

###  query查询

#### 匹配

> match: 分词匹配
>
> match_all: 全部匹配
>
> match_phrase: 精确配置

* 示例：

  ```http
  GET  http://localhost:9200/userinfo/_doc/_search?q=name:张三
  ```

  结果:

  ```json
  {
      "took": 3,
      "timed_out": false,
      "_shards": {
          "total": 1,
          "successful": 1,
          "skipped": 0,
          "failed": 0
      },
      "hits": {
          "total": {
              "value": 1,
              "relation": "eq"
          },
          "max_score": 1.605183,
          "hits": [
              {
                  "_index": "userinfo",
                  "_type": "_doc",
                  "_id": "Xh5K-HwBwyapKc96Vzs0",
                  "_score": 1.605183,
                  "_source": {
                      "name": "张三",
                      "age": 40,
                      "sex": "女的"
                  }
              }
          ]
      }
  }
  ```

### body体查询

* 示例:

  ```HTTP
  GET http://localhost:9200/userinfo/_doc/_search
  {
      "query":{
         "match":{
             "name":"三" // name字段类型为text，会进行分词，所以可以按分词查询
         }
      }
  }
  ```

  结果:

  ```
  {
      "took": 2,
      "timed_out": false,
      "_shards": {
          "total": 1,
          "successful": 1,
          "skipped": 0,
          "failed": 0
      },
      "hits": {
          "total": {
              "value": 1,
              "relation": "eq"
          },
          "max_score": 0.8025915,
          "hits": [
              {
                  "_index": "userinfo",
                  "_type": "_doc",
                  "_id": "Xh5K-HwBwyapKc96Vzs0",
                  "_score": 0.8025915,
                  "_source": {
                      "name": "张三",
                      "age": 40,
                      "sex": "女的"
                  }
              }
          ]
      }
  }
  ```

## 集群的内部原理

### 集群健康

* 示例：

  ```http
  GET http://localhost:9200/_cluster/health
  ```

  结果：

  ```json
  {
      "cluster_name": "elasticsearch",
      "status": "yellow",
      "timed_out": false,
      "number_of_nodes": 1,
      "number_of_data_nodes": 1,
      "active_primary_shards": 3,
      "active_shards": 3,
      "relocating_shards": 0,
      "initializing_shards": 0,
      "unassigned_shards": 3,
      "delayed_unassigned_shards": 0,
      "number_of_pending_tasks": 0,
      "number_of_in_flight_fetch": 0,
      "task_max_waiting_in_queue_millis": 0,
      "active_shards_percent_as_number": 50.0
  }
  ```

  status包括： green、yellow、red

  **green**：所有的主分片和副本分片都正常运行。

  **yellow**：所有的主分片都正常运行，但不是所有的副本分片都正常运行。

  **red**：有主分片没能正常运行。

### 创建索引

* 示例：

  ```http
  PUT http://localhost:9200/blogs
  {
     "settings" : {
        "number_of_shards" : 3, // 3个分片
        "number_of_replicas" : 1 // 一个副本
     }
  }
  ```

### 获取多个文档

* 示例:

  ```http
  GET localhost:9200/_mget?pretty
  {
     "docs" : [
        {
           "_index" : "userinfo",
           "_type" :  "_doc",
           "_id" :    1001
        },
        {
           "_index" : "userinfo",
           "_type" :  "_doc",
           "_id" :    1002,
           "_source": "name"
        }
     ]
  }
  '
  
  ```

  



# 参考资料

[官方文档](https://www.elastic.co/guide/cn/index.html)







