# CDSDataProxy

## Introduction

This program is a proxy service aiming to solve the problem that some Online Judge platforms (such as PTA) used during competitions, when paired with the [hydro-dev/xcpc-tools](https://github.com/hydro - dev/xcpc - tools), cannot generate balloon information in accordance with the API specifications of Domjudge.

This program organizes balloon information in line with Domjudge's API specifications by returning information to the CDS server and combining it with an Excel sheet containing team information. Thus, even when the OJ platform doesn't provide balloon information, it can correctly organize information for balloon distribution during the competition (including seat numbers).

## Principle

By combining the `submissions`, `problems`, and `judgements` returned by CDS with the local Excel, and faking the returned information into the API interface of Domjudge (`/api/v4/contest/{cid}/awards`), it achieves compatibility with `hydro - dev/xcpc - tools`.

A sample of the returned JSON is as follows:

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

## Usage

**Note: This program is compiled with Java 21**

```bash
java -jar -Xmx128m CDSDataProxy - 1.0.0.jar
```

After the first startup, `config.yml` and `teams.xlsx` will be generated. Please fill in `teams.xlsx` according to the given sample. For `config.yml`, the explanations are as follows:

```yaml
# The base URL of the CDS (Contest Data System) API
# The application will access contest data, such as submission records and team information, through this URL
cdsApiUrl: https://127.0.0.1

# The port number of the CDS being listened to currently.
serverPort: 8443

# The unique identifier (ID) of the target contest
# The program will obtain data for a specific contest around this ID, such as the problems, submissions, and teams of that contest
contestId: 12

# The ID of the administrator account used to access the CDS API
adminID: admin

# The password corresponding to the administrator account
adminPassword: adm1n

# Whether to ignore SSL certificate verification
ignoreSSL: true

# The port number of the CDS forwarded by the program
proxyPort: 5000

# The name of the local team information Excel file.
teamsFile: teams.xlsx
```

The following need to be modified in the configuration of `hydro-dev/xcpc-tools`:

```yaml
type: domjudge
server: "http://127.0.0.1:5000"
contestId: "12"
username: admin
password: adm1n
```

## Special Thanks

DSA Laboratory of Chengdu University of Information Technology
Modding Craft ZBD Studio
SpringBoot
JetBrains IntelliJ IDEA

## License

MIT LICENSE