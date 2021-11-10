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

​	Elasticsearch分布式搜索和分析引擎是Elastic Stack的核心。Logstash和Beats有助于收集、聚合和丰富数据，并将其存储在Elasticsearch中。Kibana能够交互式地探索、可视化和共享数据，并管理和监控堆栈。Elasticsearch可以索引、搜索和分析数据，可扩展到上百台服务器，处理PB级的数据。

​	Elasticsearch为所有类型的数据提供**近乎实时**的搜索和分析。无论是结构化或非结构化文本、数字数据还是地理空间数据，Elasticsearch都能以快速搜索的方式有效地存储和索引它们。可以数据检索和聚合信息。随着数据和查询量的增长，Elasticsearch的分布式特性可以使的部署能够与之无缝地增长。

<!--more-->

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

## 索引



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

### 静态索引配置

静态索引配置列表（非特定索引）:

* index.number_of_shards

  索引的主分片数，默认1。该设置只能在创建索引是设置。

  📢 每个索引的分片数量被限制在1024。

* index.number_of_routing_shards

  与`index.number_of_shards`一起使用，路由文档到主分片。

* index.codec

  设置压缩数据格式，默认LZ4。可以设置`best_compression`更高的压缩比，但是存储性能降低。更新压缩类型，新的压缩类型将在段合并后应用，也可以强制合并。

### 动态索引配置



## Mapping

### 字段类型

ES数据存储的是`JSON`串。

##### Alias

设置字段的别名，可用于搜索。

示例：

```
PUT trips
{
  "mappings": {
    "properties": {
      "distance": {
        "type": "long"
      },
      "route_length_miles": {
        "type": "alias",
        "path": "distance" 
      },
      "transit_mode": {
        "type": "keyword"
      }
    }
  }
}
```

```

PUT /trips/_doc/1
{
  "distance": 1,
  "transit_mode":"transit_mode"
}
```

```
GET /trips/_doc/_search
{
  "query":{
    "match":{
      "route_length_miles":1
    }
  }
}
```

#### Arrays

ES没有专门的数组数据类型，任何字段都可以有一个或多个值，但是数据类型必须相同。

示例：

```
PUT /trips/_doc/2
{
  "distance": [1,2],
  "transit_mode":"transit_mode"
}
```

结果:

```

  "_index" : "trips",
  "_type" : "_doc",
  "_id" : "2",
  "_version" : 1,
  "_seq_no" : 1,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "distance" : [
      1,
      2
    ],
    "transit_mode" : "transit_mode"
  }
}
```

#### Binary

二进制类型接受二进制值作为Base64编码的字符串。该字段默认不存储，不可搜索。

示例:

```
PUT my-index-000001
{
  "mappings": {
    "properties": {
      "name": {
        "type": "text"
      },
      "blob": {
        "type": "binary"
      }
    }
  }
}
```

```
PUT my-index-000001/_doc/1
{
  "name": "Some binary blob",
  "blob": "U29tZSBiaW5hcnkgYmxvYg==" 
}
```

结果:

```
{
  "_index" : "my-index-000001",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 1,
  "_seq_no" : 0,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "name" : "Some binary blob",
    "blob" : "U29tZSBiaW5hcnkgYmxvYg=="
  }
}
```

#### boolean

true、false、其他可接收的bool值。

示例:

```
PUT my-index-000002
{
  "mappings": {
    "properties": {
      
      "bool":{
        "type": "boolean"
      }
    }
  }
}
```

```
PUT my-index-000002/_doc/1
{
 "bool":[
   true,
   false,
   "",
   "true",
   "false"
   ]
}
```

#### Date

时间格式字符换、时间戳、纪元时间。

示例：

```
PUT my-index-000001
{
  "mappings": {
    "properties": {
      "date": {
        "type": "date"
      }
    }
  }
}
```

```
PUT my-index-000001/_doc/1
{ 
  "date": [
    "2015-01-01 00:00:00",
    "2015-01-01T12:10:30Z",
    1420070400001
    ]
}
```

设置日期格式：

```
"format": "strict_date_optional_time||epoch_second"
```





**Common types**

* binary

  二进制值编码为Base64字符串。

* boolean

  `ture` or `false`

* Keywords家族

  包括：`keyword`、`constant_keyword`、`wildcard`

  

### mapping limt setting

* index.mapping.total_fields.limit

  索引字段最大数的限制。包括字段、对象的映射，字段别名等。默认值1000。

  限制防止映射和搜索过大导致性能下降和内存问题。

  

## 索引模板

​	索引模板是在创建索引是告诉ES如何配置索引，对与数据流，索引在写入数据是创建。模板在创建索引前设置，当创建索引时，模板将作为创建索引的基础。

​	有两种模板：**索引模板**和**组件模板**。组件模板是可重用的构建块，用于配置mapping、setting和aliases。虽然可以使用组件模板构造索引模板，但是他们不能直接应用于一组索引。索引模板可以包含组件模板的集合，也可以直接配置mapping、setting和aliases。

索引模板使用条件：

* 组件模板优先于遗留模板(?)。如果没有组件索引和索引匹配，则遗留模板任然可以匹配使用。
* 如果显示的设置创建的索引的模板索引，则显示设置的优先于索引模板和组件索引。
* 当新建数据流配置到多个模板时，优先级最高的模板被使用。

**避免索引模板冲突**

内置的索引模板，优先级为100，如下：

- `logs-*-*`
- `metrics-*-*`
- `synthetics-*-*`

### 组件模板

* 创建两个组件模板：

```http
PUT _component_template/component_template1
{
  "template": {
    "mappings": {
      "properties": {
        "@timestamp": {
          "type": "date"
        }
      }
    }
  }
}

```

```
PUT _component_template/runtime_component_template
{
  "template": {
    "mappings": {
      "runtime": { 
        "day_of_week": {
          "type": "keyword",
          "script": {
            "source": "emit(doc['@timestamp'].value.dayOfWeekEnum.getDisplayName(TextStyle.FULL, Locale.ROOT))"
          }
        }
      }
    }
  }
}
```

​	说明：

​		`runtime`:当索引匹配到模板时，会给索引动态添加`day_of_week`字段。	

* 获取组件模板

  ```
  GET _component_template/component_template1
  ```

  结果:

  ```
  {
    "component_templates" : [
      {
        "name" : "component_template1",
        "component_template" : {
          "template" : {
            "mappings" : {
              "properties" : {
                "@timestamp" : {
                  "type" : "date"
                }
              }
            }
          }
        }
      }
    ]
  }
  ```

### 索引模板

* 创建索引模板：

```
PUT _index_template/template_1
{
  "index_patterns": ["te*", "bar*"],
  "template": {
    "settings": {
      "number_of_shards": 1
    },
    "mappings": {
      "_source": {
        "enabled": true
      },
      "properties": {
        "host_name": {
          "type": "keyword"
        },
        "created_at": {
          "type": "date",
          "format": "EEE MMM dd HH:mm:ss Z yyyy"
        }
      }
    },
    "aliases": {
      "mydata": { }
    }
  },
  "priority": 500,
  "composed_of": ["component_template1", "runtime_component_template"], 
  "version": 3,
  "_meta": {
    "description": "my custom"
  }
}
```

说明：

`index_patterns`:索引匹配规则

`template`： 索引模板结构

`composed_of`：模板组合

* 获取索引模板

  ```http
  GET _index_template/template_1
  ```

  结果：

  ```json
  {
    "index_templates" : [
      {
        "name" : "template_1",
        "index_template" : {
          "index_patterns" : [
            "te*",
            "bar*"
          ],
          "template" : {
            "settings" : {
              "index" : {
                "number_of_shards" : "1"
              }
            },
            "mappings" : {
              "_source" : {
                "enabled" : true
              },
              "properties" : {
                "created_at" : {
                  "format" : "EEE MMM dd HH:mm:ss Z yyyy",
                  "type" : "date"
                },
                "host_name" : {
                  "type" : "keyword"
                }
              }
            },
            "aliases" : {
              "mydata" : { }
            }
          },
          "composed_of" : [
            "component_template1",
            "runtime_component_template"
          ],
          "priority" : 500,
          "version" : 3,
          "_meta" : {
            "description" : "my custom"
          }
        }
      }
    ]
  }
  ```

* 创建索引

  ```
  PUT /test1
  ```

  索引内容：

  ```
  {
    "test1" : {
      "aliases" : {
        "mydata" : { }
      },
      "mappings" : {
        "runtime" : {
          "day_of_week" : {
            "type" : "keyword",
            "script" : {
              "source" : "emit(doc['@timestamp'].value.dayOfWeekEnum.getDisplayName(TextStyle.FULL, Locale.ROOT))",
              "lang" : "painless"
            }
          }
        },
        "properties" : {
          "@timestamp" : {
            "type" : "date"
          },
          "created_at" : {
            "type" : "date",
            "format" : "EEE MMM dd HH:mm:ss Z yyyy"
          },
          "host_name" : {
            "type" : "keyword"
          }
        }
      },
      "settings" : {
        "index" : {
          "routing" : {
            "allocation" : {
              "include" : {
                "_tier_preference" : "data_content"
              }
            }
          },
          "number_of_shards" : "1",
          "provided_name" : "test1",
          "creation_date" : "1636514509605",
          "number_of_replicas" : "1",
          "uuid" : "P8F8MCvOQbS6GrIveSJ3dw",
          "version" : {
            "created" : "7130299"
          }
        }
      }
    }
  }
  ```

# match、term、filter、query区别

* `term`:精确匹配，不进行分词
* `match`：分词模糊匹配
* `filter`:没有相关度，要么有数据，有么null
* `query`:相关度计算













