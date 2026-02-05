---
layout: page
title: 周刊
permalink: /weekly/
---

<section class="home-hero">
  <div class="hero-title">Weekly Notes</div>
  <div class="hero-subtitle">每周学习与实践沉淀</div>
  <p class="hero-desc">这里收录每周的阅读、实验与小结，方便长期回顾与整理。</p>
</section>

<section class="home-main" style="margin-top: 24px;">
  <h2 class="section-title">最新周刊</h2>
  <div class="post-grid">
    {% assign weekly_posts = site.weekly | where_exp: "post", "post.published == true" | sort: "date" | reverse %}
    {% for post in weekly_posts limit: 6 %}
      <a class="post-card post-link-card" href="{{ post.url | relative_url }}">
        <div class="post-meta">{{ post.date | date: "%Y-%m-%d" }}</div>
        <div class="post-title">{{ post.title }}</div>
        <p class="post-excerpt">{{ post.summary | default: post.excerpt | strip_html | truncate: 120 }}</p>
      </a>
    {% endfor %}
  </div>
</section>
