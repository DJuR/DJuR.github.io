---
layout: post
title:  windows 下源码编译 PHP
categories: 学习笔记
tag: php
---
* content
{:toc}

## windows 下源码编译 PHP

### 具体步骤

1、获取 php-sdk 工具包

```
git clone https://github.com/Microsoft/php-sdk-binary-tools.git d:\php-sdk
```

2、进入工具包切换指定分支

```
cd d:\php-sdk
git checkout php-sdk-2.1.9
```

3、执行 `phpsdk-vc15-x64.bat`

``` 
phpsdk-vc15-x64.bat
``` 

4、 执行 `phpsdk_buildtree phpdev`

> 注： 我是将 `d:\php-sdk\bin` 加入环境变量中 `PATAH` 中

``` 
phpsdk_buildtree phpdev
```

* 执行后自动跳转至 `d:\php-sdk\phpdev\vs16\x64`

 5、 获取源码
 
 ``` 
git clone https://github.com/php/php-src.git && cd php-src
 ```
 
6、 执行下面操作

``` 
phpsdk_deps --update --branch master
```

or 

``` 
phpsdk_deps --update --branch X.Y [x.y为分支名]
```

7、可以像在 `Linux` 系统一样编译 `PHP` 源码了

``` 
buildconf && configure --enable-cli && nmake
```

### 参考资料

* [https://windows.php.net](https://windows.php.net/)
* [wiki.php.net](https://wiki.php.net/internals/windows/stepbystepbuild_sdk_2)
* [php-sdk-binary-tools](https://github.com/microsoft/php-sdk-binary-tools/tree/php-sdk-2.2.0beta6)
