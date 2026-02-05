# DJuR

个人主页站点：https://DJuR.github.io

## 技术栈

- Jekyll（静态站点生成器）
- Ruby
- Bundler（依赖管理器）
- Minima 主题

## 本地开发

### 环境准备

1. 安装 Ruby：https://www.ruby-lang.org/en/documentation/installation/
2. 安装 Bundler：

```powershell
gem install bundler
```

3. 安装依赖：

```powershell
bundle install
```

### 启动命令

在项目根目录运行：

```powershell
bundle exec jekyll serve --livereload
```

然后访问 `http://127.0.0.1:4000/`。

### 开发说明（无需每次重启）

Jekyll 默认会监视文件变化并自动重建页面。也就是说：

- 修改页面、样式或文章后，浏览器刷新即可看到更新
- 不需要每次都重新启动 `jekyll serve`

仅在以下情况才需要停止并重启服务：

- 修改了 `_config.yml`
- 修改了 `Gemfile` 或依赖版本
- 变更了与构建流程相关的插件

### 构建命令

```powershell
bundle exec jekyll build
```

静态文件会输出到 `_site/`。

## 部署

本项目使用 GitHub Pages 部署。推送到 `main` 分支后会自动构建并发布。

## License

Open sourced under the [MIT license](LICENSE).
