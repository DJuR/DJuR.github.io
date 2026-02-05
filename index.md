---
layout: home
---

<div class="home-landing">
  <section class="home-hero">
    <div class="hero-title">DJUR BLOG</div>
    <div class="hero-subtitle">Thinking with Systems &amp; AI</div>
    <p class="hero-desc">专注工程、系统与智能实践的长期笔记。</p>
    <div class="hero-actions">
      <a class="hero-btn" href="/weekly/">查看周刊</a>
      <a class="hero-link" href="/blog/">最新文章 →</a>
    </div>
  </section>

  <section class="home-grid">
    <div class="home-main">
      <h2 class="section-title">最新内容</h2>
      <div class="post-grid">
        {% assign all_posts = site.posts | concat: site.weekly | concat: site.notes | concat: site.ai %}
        {% assign all_posts = all_posts | where_exp: "post", "post.published == true" | sort: "date" | reverse %}
        {% for post in all_posts limit: 6 %}
          <article class="post-card">
            <div class="post-meta">
              {{ post.date | date: "%Y-%m-%d" }}
              {% if post.collection and post.collection != "posts" %}
                <span class="post-badge">{{ post.collection }}</span>
              {% endif %}
            </div>
            <a class="post-title" href="{{ post.url | relative_url }}">{{ post.title }}</a>
            <p class="post-excerpt">{{ post.summary | default: post.excerpt | strip_html | truncate: 120 }}</p>
          </article>
        {% endfor %}
      </div>
      <div class="home-more">
        <a class="hero-link" href="/blog/">查看更多文章 →</a>
      </div>
    </div>

    <aside class="home-side">
      <div class="side-card">
        <div class="side-title">导航</div>
        <ul class="side-links">
          {%- for link in site.navigation -%}
            {%- unless link.external -%}
              <li><a href="{{ link.url | relative_url }}">{{ link.text | escape }}</a></li>
            {%- endunless -%}
          {%- endfor -%}
        </ul>
      </div>

      <div class="side-card">
        <div class="side-title">周刊</div>
        <p class="side-desc">记录每周学习与实践的沉淀。</p>
        <a class="side-cta" href="/weekly/">进入周刊</a>
      </div>

      <div class="side-card">
        <div class="side-title">外部链接</div>
        <ul class="side-links">
          <li><a href="https://github.com/DJuR" target="_blank">GitHub</a></li>
        </ul>
      </div>
    </aside>
  </section>
</div>
