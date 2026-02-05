---
layout: page
title: 笔记
permalink: /notes/
---

<div class="home-landing">
<section class="home-hero">
  <div class="hero-title">Notes</div>
  <div class="hero-subtitle">工程与系统化思考笔记</div>
  <p class="hero-desc">零散灵感、方法论与工程记录的集中展示。</p>
</section>
</div>

<section class="home-main" style="margin-top: 24px;">
  <h2 class="section-title">最新笔记</h2>
  <div class="post-grid">
    {% assign note_posts = site.notes | where_exp: "post", "post.published == true" | sort: "date" | reverse %}
    {% for post in note_posts limit: 6 %}
      <a class="post-card post-link-card" href="{{ post.url | relative_url }}">
        <div class="post-meta">{{ post.date | date: "%Y-%m-%d" }}</div>
        <div class="post-title">{{ post.title }}</div>
        <p class="post-excerpt">{{ post.summary | default: post.excerpt | strip_html | truncate: 120 }}</p>
      </a>
    {% endfor %}
  </div>
</section>
