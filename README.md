# CDSDataProxy

## 介绍

本程序是用于解决部分赛时Online Judge平台（比如PTA）在搭配[hydro-dev/xcpc-tools](https://github.com/hydro-dev/xcpc-tools)工具使用的时候无法按照Domjudge的API规范生成气球信息的代理服务。

本程序通过返回给CDS服务器的信息，结合含有队伍信息的Excel表以Domjudge的API规范来组织Balloons的信息，这样即使在OJ平台不提供Balloons信息时，也能正确组织出可供赛时发放气球的信息（包含座位号）。

## 原理

通过CDS返回的`submissions`, `problems`, `judgements`结合本地Excel, 并把返回信息转换到Domjudge的API接口(`/api/v4/contest/{cid}/awards`)以实现兼容`hydro-dev/xcpc-tools`。

返回的Json样例如下:

```json
[
  {
    "balloonid": 1,
    "time": "1764043310.366000000",
    "problem": "A",
    "team": "Still worrying about her?",
    "location": "A01",
    "affiliation": "Chengdu University of Information Technology",
    "category": "Ranked",
    "done": false,
    "contestproblem": {
      "statement": [],
      "attachments": [],
      "id": 1,
      "label": "A",
      "name": "Hello World",
      "ordinal": 1,
      "color": "Red",
      "rgb": "#ff3b30",
      "time_limit": 1,
      "short_name": "A"
    },
    "teamid": 1,
    "affiliationid": 1,
    "categoryid": 1
  },
  {
    "balloonid": 2,
    "time": "1764043311.860000000",
    "problem": "B",
    "team": "UESTC_desuwa",
    "location": "A17",
    "affiliation": "University Of Electronic Science And Technology Of China",
    "category": "Unranked",
    "done": false,
    "contestproblem": {
      "statement": [],
      "attachments": [],
      "id": 2,
      "label": "B",
      "name": "Easy",
      "ordinal": 1,
      "color": "Green",
      "rgb": "#00ff00",
      "time_limit": 1,
      "short_name": "B"
    },
    "teamid": 2,
    "affiliationid": 2,
    "categoryid": 2
  }
]
```

## 使用方法

**注意：本程序使用Java 21编译**

```bash
java -jar -Xmx128m CDSDataProxy-1.0.0.jar
```

首次启动后会生成`config.yml`和`teams.xlsx`，`teams.xlsx`请按照给的样例填写; 对于`config.yml`，解释如下:

```yaml
# CDS (Contest Data System) API 的基础 URL
# 该应用将通过此 URL 访问竞赛数据，如提交记录、队伍信息等
cdsApiUrl: https://127.0.0.1

# 当前监听的CDS端口号。
serverPort: 8443

# 目标竞赛的唯一标识符 (ID)
# 程序将围绕此 ID 获取特定竞赛的数据，例如该竞赛的题目、提交、队伍等
contestId: 12

# 用于访问 CDS API 的管理员账号 ID
adminID: admin

# 管理员账号对应的密码
adminPassword: adm1n

# 是否忽略 SSL 证书验证
ignoreSSL: true

# 程序转发的CDS端口号
proxyPort: 5000

# 本地队伍信息 Excel 文件的名称。
teamsFile: teams.xlsx
```

在`hydro-dev/xcpc-tools`的配置中需要修改的如下:

```yaml
type: domjudge
server: "http://127.0.0.1:5000"
contestId: "12"
username: admin
password: adm1n
```

## 特别感谢

成都信息工程大学DSA实验室

Modding Craft ZBD Studio

SpringBoot

JetBrains IntelliJ IDEA

## 许可证

MIT LICENSE


