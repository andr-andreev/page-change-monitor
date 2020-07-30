# Page Change Monitor [![Build Status](https://api.travis-ci.org/andr-andreev/page-change-monitor.svg?branch=master)](https://travis-ci.org/github/andr-andreev/page-change-monitor)

Page Change is a web page monitoring app based on Spring Boot.

It is useful for monitoring jobs, price changes, seasonal promos, news, etc.

![Dashboard](docs/screenshots/dashboard.png?raw=true "Dashboard")
---
![Page history](docs/screenshots/page-history.png?raw=true "Page history")

### Features
* Block filter (ignore everything except the text between the markers)
* One RSS feed for all pages changes

### Installation
```bash
git clone https://github.com/andr-andreev/page-change-monitor.git
cd page-change-monitor
mysql page_change_monitor < src/main/resources/db/mysql/schema.sql
./mvnw spring-boot:run
```
You will be able to access the app at http://localhost:8080/.

### Available Settings
| Setting               | Default value   |
|-----------------------|-----------------|
| `app.cron-expression` | `0 0 * * * ?`   |
| `app.rss-item-count`  | `25`            |