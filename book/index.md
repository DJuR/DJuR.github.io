---
layout: page
title: 文档
description: DJuR 文档模块
permalink: /book/
---

<div class="home-landing">
<section class="home-hero">
  <div class="hero-title">Book Series</div>
  <div class="hero-subtitle">专题系列与长文档</div>
  <p class="hero-desc">每个系列是一个主题包，包含目录页与多篇章节。</p>
</section>

<section class="home-main" style="margin-top: 24px;">
  <h2 class="section-title">系列列表</h2>
  <div class="post-grid">
   {% assign published_books = site.books | where_exp: "item", "item.published == true" %}
  {% assign series_list = published_books
    | where_exp: "item", "item.type == 'series' or item.path contains '/index.md'"
    | sort: "order"
  %}

    {% for item in series_list %}
      <a class="post-card post-link-card" href="{{ item.url | relative_url }}">
        <div class="post-meta">{{ item.date | date: "%Y-%m-%d" }}</div>
        <div class="post-title">{{ item.title }}</div>
        <p class="post-excerpt">{{ item.summary | default: item.excerpt | strip_html | truncate: 120 }}</p>
      </a>
    {% endfor %}
  </div>
</section>
</div>
