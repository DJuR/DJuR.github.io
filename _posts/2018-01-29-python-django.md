---
layout: page
title: python Django 框架
categories: [python]
---

## python Django 框架

__Django__ 是一个高级的 Python Web框架，具有快速开发、干净、实用的设计等特性，它解决Web开发的许多常见问题，让开发者专注于应用程序。

* 安装 Django 或版本替换

    ```
    $ pip3 install Django==2.0.1
    ```

* 卸载 Django

    卸载 Django直接删除Django包即可，通过下面命令查看Django安装路径 

    ```
    $ python3 -c "import django;print(django.__path__)"
    ```

* 查看版本

    ```
    $ python -m django --version
    ```

## 创建项目

* 创建一个mysite项目

    ```
    $ django-admin.py startproject mysite
    ```

    执行后的目录结构:

    ```
    mysite/
        manage.py
        mysite/
            __init__.py
            settings.py
            urls.py
            wsgi.py
    ```

    mysite/: 项目目录

    manage.py: 管理工具
    
    mysite/: 项目的Python目录
    
    mysite/__init__.py: 一个空的文件，告诉python这个目录包含python包
    
    mysite/settings.py: 项目的配置
    
    mysite/urls.py: 项目URL的声明
    
    mysite/wsgi.py: web服务的入口

* 运行项目

    ```
    $ python3 manage.py runserver
    ```

    浏览器访问`http://127.0.0.1:8000`

    默认端口8000,可设置制定端口:

    ```
    $ python3 manage.py runserver 8080
    ```


* 创建Polls应用：

    ```
    $ python3 manage.py startapp polls
    ```

    生成polls目录结构:

    ```
    polls/
        __init__.py
        admin.py
        apps.py
        migrations/
            __init__.py
        models.py
        tests.py
        views.py
    ```

   
* 写第一个view文件

    打开polls/views.py写入：

    ```
    from django.http import HttpResponse

    def index(request):
        return HttpResponse("Hello, world. You're at the polls index.")

    ```

* 创建一个url配置 `polls/urls.py`:

    ```
    from django.urls import path

    from . import views

    urlpatterns = [
        path('', views.index, name='index'),
    ]
    ```

    在mysite/urls.py中设置项目polls的url:

    ```
    from django.urls import include, path
    from django.contrib import admin

    urlpatterns = [
        path('polls/', include('polls.urls')),
        path('admin/', admin.site.urls),
    ]
    ```

    浏览器访问:`http://127.0.0.1:8080/polls`

## 数据库设置

mysite/settings.py 项目变量配置文件默认的是SQLite数据库配置(SQLite包含在Python中)

Django已经把后端模块帮我准备好了，只需要执行数据库同步，把相关表生成出来即可：

```
$ python3 manage.py migrate
```

* 创建后端管理员账号:

    ```
    $ python manage.py createsuperuser
    $ Username (leave blank to use 'fnngj'): admin    # 管理员帐号
    $ Email address: admin@mail.com      # email
    $ Password:                          # 密码
    $ Password (again):                  # 重复密码
    $ Superuser created successfully.
    ```

* 创建models `polls/models.py`:

    ```
    from django.db import models


    class Question(models.Model):
        question_text = models.CharField(max_length=200)
        pub_date = models.DateTimeField('date published')


    class Choice(models.Model):
        question = models.ForeignKey(Question, on_delete=models.CASCADE)
        choice_text = models.CharField(max_length=200)
        votes = models.IntegerField(default=0)
    ```
    

编辑`nysite/setting.py`文件，添加polls应用:

```
 INSTALLED_APPS = [
    'polls.apps.PollsConfig',
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
]
```


## 扩展阅读 ##

- [Django 官网](http://www.djangoproject.com)
