---
layout: home
---


./文章
----------


<ul>
  {% for post in site.posts limit:=200 %}
    <li class="alink">
      <a href="{{ post.url }}" class="red-link">
        {{ post.date | date: "%Y-%m-%d" }}&emsp;{{ post.title }}
      </a>
    </li>
  {%- endfor -%}
  <li class="alink"><a href="./blog/" class="red-link">&hellip;&hellip;</a></li>
</ul>


# ./参考
[1] <a href="http://hollischuang.gitee.io/tobetopjavaer/" target="_blank">Java工程师成神之路</a>

