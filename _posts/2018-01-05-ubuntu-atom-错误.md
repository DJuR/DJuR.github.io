---
layout: default
title: ubuntu安装atom apt 不可用
categories: [test, sample, tool]
---

ubuntu安装atom官方下载的安装包，apt 报错:

```
E: 软件包 atom 需要重新安装，但是我无法找到相应的安装文件
```

各种autoremove、 remove 、purge都不行

解决方法:

```
$ sudo dpkg --remove --force-remove-reinstreq atom
```

<!-- more -->