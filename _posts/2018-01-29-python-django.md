---
layout: page
title: python Django 框架
categories: [python]
---

## python Django 框架

__Django__ 是一个高级Python Web框架，快速开发、干净、实用的设计，解决Web开发的许多麻烦，让开发者专注于应用程序。

* 安装 Django 或版本替换

```
$ pip3 install Django==2.0.1
```

* 卸载 Django

卸载 Django直接删除Django包即可,下面命令查看Django安装路径 

```
$ python3 -c "import django;print(django.__path__)"
```


## Django 创建项目

* 创建一个myDj项目和blog应用

创建项目：

```
$ django-admin.py startproject myDj
```

创建应用：

```
$ cd myDj
$ python3 manag.py startapp blog
```

打开`settings.py`配置 blog 应用:

```
# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'blog',
]
```

* 配置`settings.py`初始化admin后台数据库

```
# Database
# https://docs.djangoproject.com/en/2.0/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}
```

Python 自带SQLite3数据库

```
# Database
# https://docs.djangoproject.com/en/2.0/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}
```

Django默认帮我们做很多事情，比如User、Session 这些都需要创建表来存储数据，Django已经把这些模块帮我准备好了，我们只需要执行数据库同步，把相关表生成出来即可：

```
$  python manage.py migrate
```

创建管理员账号:

```
$ python manage.py createsuperuser
$ Username (leave blank to use 'fnngj'): admin    # 管理员帐号
$ Email address: admin@mail.com      # email
$ Password:                          # 密码
$ Password (again):                  # 重复密码
$ Superuser created successfully.
```

* 运行项目

```
$ python3 manage.py runserver 0.0.0.0:8000
```

浏览器访问`http://127.0.0.1:8000`

* 视图和URL配置

在HelloWorld 目录新建一个 view.py 文件，并输入代码：

```
from django.http import HttpResponse
 
def hello(request):
    return HttpResponse("Hello world ! ")
```

绑定 URL 与视图函数:

```
from django.conf.urls import url
 
from . import view
 
urlpatterns = [
    url(r'^$', view.hello),
]
```

## 扩展阅读 ##

- [Django 官网](http://www.djangoproject.com)
