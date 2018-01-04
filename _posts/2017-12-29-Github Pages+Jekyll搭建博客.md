---
layout: default
title: Github Pages + Jekyll 搭建博客
categories: [test, sample]
tags: [sss,ss]
---

sdsdsdsdsd

<!-- more -->
## Github Pages + Jekyll 搭建博客 ##

### Github Pages ###

#### 1. Create a repository ####

![](https://i.imgur.com/k4BR08Z.png)

#### 2. What git lient are you uesing? ####
- A terminal

![](https://i.imgur.com/aGBKNeK.png)

- GitHub for Windows

![](https://i.imgur.com/CSsbZPq.png)

- GitHub for Mac

![](https://i.imgur.com/Q6ghY3S.png)

- I Don't Know

![](https://i.imgur.com/wFWtxb0.png)

#### 3. ... and you're done! ####

![](https://i.imgur.com/2qAOoFy.png)

### Jekyll 搭建博客
* 安装 Ruby

`
sudo apt install ruby
`

* 安装 jekyll

`
sudo gem install jekyll
`

不成功尝试安装后在安装jekyll

`
sudo apt install ruby-dev
`

* 创建 Gemfile　添加内容

```
source 'https://gems.ruby-china.org/'
gem 'github-pages', group: :jekyll_plugins

```

* 安装 bundler
```
gem install bundler
```



进入项目目录,运行
`
jekyll serve
`

### Jekyll Themes 替换 ###

替换相应的文件

### 扩展阅读 ###

- [Github Pages](https://pages.github.com)
- [Jekyll](https://jekyllrb.com)
- [Jekyll Themes](http://jekyllthemes.org)
- Ruby&emsp;[中文](https://www.ruby-lang.org/zh_cn/)&emsp;[English](https://www.ruby-lang.org/en/)
- [RGems](https://rubygems.org/)&emsp;[RubyGems-Ruby China](http://gems.ruby-china.org)
- [Customizing Github Pages](https://help.github.com/categories/customizing-github-pages/)
- [搭建一个免费的，无限流量的Blog----github Pages和Jekyll入门【阮一峰】](http://www.ruanyifeng.com/blog/2012/08/blogging_with_jekyll.html)
