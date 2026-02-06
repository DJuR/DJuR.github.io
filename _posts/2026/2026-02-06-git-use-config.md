---
title: "Git 使用与配置全指南"
date: 2026-02-06
summary: "本文记录 Git 常用配置、项目级与全局配置、代理设置、缓存清理、多账户管理以及基本 rebase 使用"
tags: [工具]
layout: post
categories: 工具
published: true
---


# Git 使用与配置全指南

> 本文记录 Git 常用配置、项目级与全局配置、代理设置、缓存清理、多账户管理以及基本 rebase 使用。

---

## 目录

* [1. Git 配置概览](#1-git-配置概览)
* [2. Git 配置等级](#2-git-配置等级)
* [3. 查看当前配置](#3-查看当前配置)
* [4. 用户信息配置](#4-用户信息配置)
* [5. 代理配置](#5-代理配置)
* [6. 常用 Git 配置项](#6-常用-git-配置项)
* [7. 配置文件说明](#7-配置文件说明)
* [8. Git 缓存管理](#8-git-缓存管理)
* [9. 多账户 SSH / HTTPS 切换](#9-多账户-ssh--https-切换)
* [10. 基本 rebase 使用与作用](#10-基本-rebase-使用与作用)
* [11. 实战技巧](#11-实战技巧)

---

## 1. Git 配置概览

Git 配置用于定义 Git 行为和操作习惯，可以影响 commit、push、pull 等操作。
主要配置文件：

| 配置等级 | 配置文件位置                                                                  | 作用范围         |
| ---- | ----------------------------------------------------------------------- | ------------ |
| 系统级  | `/etc/gitconfig` (Linux/Mac) <br> `C:\ProgramData\Git\config` (Windows) | 对系统所有用户生效    |
| 全局级  | `~/.gitconfig` 或 `~/.config/git/config`                                 | 对当前用户生效      |
| 仓库级  | `.git/config`                                                           | 对当前 Git 仓库生效 |

> 配置优先级：**仓库级 > 用户级 > 系统级**

---

## 2. Git 配置等级

### 系统级配置

```bash
git config --system user.name "SystemUser"
git config --system user.email "system@example.com"
```

> 系统级配置需要管理员权限。

### 用户/全局配置

```bash
git config --global user.name "YourName"
git config --global user.email "you@example.com"
```

### 仓库级配置

```bash
cd /path/to/your/repo
git config user.name "ProjectUser"
git config user.email "project@example.com"
```

> 仓库级配置会覆盖全局配置。

---

## 3. 查看当前配置

```bash
# 查看全部配置
git config --list

# 查看特定配置
git config user.name
git config --get user.email

# 查看各级别配置
git config --system --list
git config --global --list
git config --local --list

# 查看配置来源
git config -l --show-origin
```

---

## 4. 用户信息配置

```bash
git config --global user.name "DJUR"
git config --global user.email "djur@example.com"
```

验证：

```bash
git config --global --list
# 输出：
# user.name=DJUR
# user.email=djur@example.com
```

---

## 5. 代理配置

### HTTP / HTTPS 代理

```bash
git config --global http.proxy http://127.0.0.1:7890
git config --global https.proxy http://127.0.0.1:7890
```

### SOCKS5 代理

```bash
git config --global http.proxy 'socks5://127.0.0.1:1080'
git config --global https.proxy 'socks5://127.0.0.1:1080'
```

### 取消代理

```bash
git config --global --unset http.proxy
git config --global --unset https.proxy
```

---

## 6. 常用 Git 配置项

```bash
# 编辑器
git config --global core.editor "code --wait"

# 差异显示颜色
git config --global color.ui auto

# 默认分支名
git config --global init.defaultBranch main

# 合并冲突显示方式
git config --global merge.conflictstyle diff3

# 缓存 HTTPS 凭证
git config --global credential.helper cache --timeout=3600

# 拉取策略
git config --global pull.rebase false      # merge 默认
git config --global pull.ff only           # 快进
```

---

## 7. 配置文件说明

```ini
[user]
    name = DJUR
    email = djur@example.com
[core]
    editor = code --wait
    autocrlf = input
[color]
    ui = auto
[http]
    proxy = http://127.0.0.1:7890
[credential]
    helper = cache --timeout=3600
[init]
    defaultBranch = main
```

---

## 8. Git 缓存管理

### 移除已缓存的文件 / 清理缓存

```bash
# 移除已缓存但未提交的文件
git rm --cached <file>

# 移除所有缓存文件（例如 .gitignore 生效）
git rm -r --cached .
git add .
git commit -m "Apply .gitignore"
```

### 清理凭证缓存

```bash
git credential-cache exit
```

---

## 9. 多账户 SSH / HTTPS 切换

### 1. SSH 多账户配置

编辑 `~/.ssh/config`：

```ssh
# GitHub 个人账号
Host github-personal
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_rsa_personal

# GitHub 工作账号
Host github-work
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_rsa_work
```

克隆仓库时使用别名：

```bash
git clone git@github-personal:username/repo.git
git clone git@github-work:company/repo.git
```

### 2. HTTPS 多账户（凭证缓存）

```bash
# 针对仓库设置不同用户
git config user.name "WorkUser"
git config user.email "work@example.com"

# 使用 credential helper 保存不同账户密码
git config credential.helper store
```

---

## 10. 基本 rebase 使用与作用

`rebase` 用于将分支修改“移动”到另一分支的最前端，保持提交历史线性。

### 1. 交互式 rebase

```bash
# 修改最近 3 次提交
git rebase -i HEAD~3
```

> 可用于合并提交、修改提交信息、删除提交。

### 2. 将分支更新到主分支

```bash
# 切到 feature 分支
git checkout feature

# 将 main 分支的最新提交移动到 feature 前端
git rebase main
```

### 3. 使用注意事项

* 已推送到远程的分支尽量不要 rebase，会破坏公共历史
* rebase 后若出现冲突，手动解决冲突并执行：

  ```bash
  git rebase --continue
  ```
* 放弃 rebase：

  ```bash
  git rebase --abort
  ```

---

## 11. 实战技巧

1. **多账户管理**

   * 仓库级配置 + SSH 配置，实现个人/工作账号切换。

2. **代理自动切换**

   * 脚本检测网络环境，自动配置代理。

3. **配置优先级检查**

   * `git config -l --show-origin` 查看每条配置来源。

4. **缓存与 .gitignore 生效**

   * `git rm -r --cached .` 配合 `.gitignore` 清理无效文件。

5. **保持提交历史整洁**

   * 使用 rebase 合并小提交，保证线性历史。

---

> 掌握 Git 配置、代理、多账户管理及 rebase 技巧，可以让开发者在多环境、多项目中高效协作，避免常见的冲突和身份混乱问题。

---

如果你愿意，我可以帮你加一段 **“一键初始化新项目 Git 高级配置脚本”**，让你新建项目时自动应用全局、仓库级配置、代理、多账户 SSH、默认分支和基本 rebase 策略，一行命令就能搞定，非常适合技术博客附带示例。

你希望我加吗？
