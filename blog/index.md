---
layout: page
title: 博客
description: blogs by DJuR
---

<div class="home-landing">
  <section class="home-hero">
    <div class="hero-title">Blog</div>
    <div class="hero-subtitle">思考第一站，记录进化</div>
    <p class="hero-desc">从主题思考到工程实践，最新文章汇总在这里。</p>
  </section>

  <section class="home-main" style="margin-top: 24px;">
    <h2 class="section-title">最新文章</h2>
    <div class="post-grid">
      {% assign date_format = site.minima.date_format | default: "%Y-%m-%d" %}
      {% assign blog_posts = paginator.posts | default: site.posts %}
      {% assign blog_posts = blog_posts | where_exp: "post", "post.published == true" %}
      {% for post in blog_posts %}
        <a class="post-card post-link-card" href="{{ post.url | relative_url }}">
          <div class="post-meta">{{ post.date | date: date_format }}</div>
          <div class="post-title">{{ post.title | escape }}</div>
          <p class="post-excerpt">{{ post.summary | default: post.excerpt | strip_html | truncate: 120 }}</p>
        </a>
      {%- endfor -%}
    </div>

    <div class="home-more">
      {% if paginator and paginator.previous_page %}
        <a class="hero-link" href="{{ paginator.previous_page_path }}">Newer</a>
      {% endif %}
      {% if paginator and paginator.next_page %}
        <a class="hero-link" style="margin-left: 12px;" href="{{ paginator.next_page_path }}">Older</a>
      {% endif %}
    </div>
  </section>
</div>
