---
layout: page
title: python 缺少tkinter包
categories: [python]
---


**python 提示缺少tkinter包:**

```
$ ImportError: No module named '_tkinter', please install the python3-tk package
$ >>> import _tkinter
$ Traceback (most recent call last):
$   File "<stdin>", line 1, in <module>
$ ImportError: No module named '_tkinter'

```

**解决:**

添加源到`/etc/apt/sources.list`

```
$ deb http://cz.archive.ubuntu.com/ubuntu xenial main 
```

执行:

```
$ sudo apt update
$ sudo apt install python3-tk
```

查看软件包:

[python3-tk](https://packages.ubuntu.com/xenial/python3-tk)
