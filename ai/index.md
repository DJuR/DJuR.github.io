---
layout: page
title: AI 实践
permalink: /ai/
---

<div class="home-landing">
<section class="home-hero">
  <div class="hero-title">AI Practice</div>
  <div class="hero-subtitle">模型应用与 Agent 实验</div>
  <p class="hero-desc">记录真实场景中的模型调用、工具编排与效率提升尝试。</p>
</section>
</div>

<section class="home-main" style="margin-top: 24px;">
  <h2 class="section-title">最新实践</h2>
  <div class="post-grid">
    {% assign ai_posts = site.ai | where_exp: "post", "post.published == true" | sort: "date" | reverse %}
    {% for post in ai_posts limit: 6 %}
      <a class="post-card post-link-card" href="{{ post.url | relative_url }}">
        <div class="post-meta">{{ post.date | date: "%Y-%m-%d" }}</div>
        <div class="post-title">{{ post.title }}</div>
        <p class="post-excerpt">{{ post.summary | default: post.excerpt | strip_html | truncate: 120 }}</p>
      </a>
    {% endfor %}
  </div>
</section>
