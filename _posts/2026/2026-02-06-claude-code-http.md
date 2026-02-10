---
layout: post
title:  " Claude Code 抓包解析请求流程"
summary: "使用 Claude Code 创建自定义 Python Skill 并解析请求流程"
categories: 工具
tags: [工具, ai]
published: true
---

在 Claude Code 中，自定义 **Skill** 可以让模型执行特定任务，例如生成固定输出 `"DJuR，hello claude"`。本文不仅介绍技能创建，还详细解析 **用户请求 → Claude → Skill → 输出** 的执行流程，以及一次请求如何生成多次增量 HTTP 请求。

---

## 1️⃣ 背景

Claude Code 提供了技能系统，允许用户：

* 定义固定功能（Skill）
* 绑定触发指令或任务
* 与模型交互时自动调用

Windows 下技能存放位置：

```
.claude/skills/ 或 %USERPROFILE%\.claude\skills\
```

---

## 2️⃣ 创建最简单 Python Skill

目标：

* 用户请求 `"创建最简单的 Python 示例程序"`
* 输出固定文本：

  ```
  DJuR，hello claude
  ```

### Skill 文件结构（Windows）

```
D:\py\tmp2\.claude\skills\hello_DJuR\SKILL.md
```

**SKILL.md 内容：**

```markdown
---
name: hello_DJuR
description: 当用户想创建或演示最简单的 Python 程序时使用，严格输出 "DJuR，hello claude"
---

# 指令说明
当用户请求创建 Python 示例代码时，返回一个最简单的 Python 程序，严格输出 "DJuR，hello claude"。
```

> 注意：`name` 必须与文件夹名一致，否则无法被 Claude Code 识别。

---

## 3️⃣ 加载 Skill

在终端执行：

```text
/skills
```

如果成功，会显示：

```
Successfully loaded skill: hello_DJuR
```

---

## 4️⃣ 用户请求到 Skill 执行的流程

用户输入：

```
创建一个最简单的 Python 示例程序
```

执行流程可分为四步：

1. **用户请求**
   用户发送消息到 Claude Code：

   ```json
   {
     "role": "user",
     "content": [
       "创建一个最简单的 Python 示例程序"
     ]
   }
   ```

2. **Claude 识别请求并匹配 Skill**
   模型返回：

   ```json
   {
     "role": "assistant",
     "content": [
       "I'll help you create a simple Python program. Let me use the appropriate skill for this.",
       {
         "type": "tool_use",
         "name": "Skill",
         "input": {
           "skill": "hello_DJuR"
         }
       }
     ]
   }
   ```

   * `tool_use` 表示模型调用 Skill。

3. **Skill 执行并返回结果**
   Skill 执行后返回：

   ```json
   {
     "role": "user",
     "content": [
       {
         "type": "tool_result",
         "tool_use_id": "call_de2f83a1c2e74e8f9c177b72",
         "text": "Launching skill: hello_DJuR\nBase directory: D:\\py\\tmp2\\.claude\\skills\\hello_DJuR\n当用户请求创建 Python 示例代码时，返回一个最简单的 Python 程序，严格输出 \"DJuR，hello claude\"。"
       }
     ]
   }
   ```

4. **Claude 输出最终结果**
   模型将 Skill 返回的文本呈现给用户：

   ```json
   {
     "role": "assistant",
     "content": [
       {
         "text": "DJuR，hello claude"
       }
     ]
   }
   ```

---

## 5️⃣ HTTP 请求增量解析

在一次请求 `"创建一个最简单的 Python 示例程序"` 中，会有 **三次增量 HTTP 请求**，对应 **messages** 中的每一阶段：

1. **系统提醒 & 用户消息**：

   ```json
   {
     "role": "user",
     "content": [
       {
         "text": "<system-reminder>以下 Skill 可用: hello_DJuR</system-reminder>"
       },
       {
         "text": "创建一个最简单的 Python 示例程序"
       }
     ]
   }
   ```

2. **模型识别 & Skill 调用**：

   ```json
   {
     "role": "assistant",
     "content": [
       {
         "text": "I'll help you create a simple Python program. Let me use the appropriate skill for this."
       },
       {
         "type": "tool_use",
         "name": "Skill",
         "input": {
           "skill": "hello_DJuR"
         }
       }
     ]
   }
   ```

3. **Skill 执行 & 模型输出**：

   ```json
   {
     "role": "user",
     "content": [
       {
         "type": "tool_result",
         "tool_use_id": "call_de2f83a1c2e74e8f9c177b72",
         "text": "Launching skill: hello_DJuR\nBase directory: D:\\py\\tmp2\\.claude\\skills\\hello_DJuR\n当用户请求创建 Python 示例代码时，返回一个最简单的 Python 程序，严格输出 \"DJuR，hello claude\"。"
       }
     ]
   },
   {
     "role": "assistant",
     "content": [
       {
         "text": "DJuR，hello claude"
       }
     ]
   }
   ```

> 每一次 HTTP 请求都是 **增量输出**（streaming），模型和 Skill 之间不断交换数据，确保实时响应。

---

## 6️⃣ 技术总结

* **Skill 文件位置**：Windows 下 `.claude/skills/技能名/SKILL.md`
* **Skill 触发规则**：通过 `description` 匹配用户请求
* **增量请求**：

  1. 系统提醒 + 用户输入
  2. 模型识别 + Skill 调用
  3. Skill 执行 + 输出最终文本
* **响应机制**：

  * `tool_use` → 调用 Skill
  * `tool_result` → Skill 返回
  * `end_turn` → 模型输出最终结果
* **应用场景**：

  * 固定文本输出
  * 自动生成模板化 Python 示例
  * 可扩展到 HTML、Flask、数据查询等 Skill

---


