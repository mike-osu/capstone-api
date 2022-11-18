# capstone-api
## CS 467 Capstone Project - Backend API

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/mike-osu/capstone-api/tree/main.svg?style=svg&circle-token=c1c9a3cf57927a025c25ceb406fac6e02b8226ca)](https://dl.circleci.com/status-badge/redirect/gh/mike-osu/capstone-api/tree/main)

---
## Background

RESTful API for [Crowd-Sourced Travel Planner](https://eecs.oregonstate.edu/capstone/submission/pages/viewSingleProject.php?id=vEoVN87FmupNY20Q) full-stack web application

Front-end: https://github.com/shallisey/capstone-ui

Serverless compute service: https://github.com/mike-osu/location-cloud-function

<img src="content/diagram.png" />

---
## How to Run
#### Requirements
- [Docker](https://www.docker.com/)

1. Clone this repo
```
$ git clone ...
$ cd capstone-api
```

2. Build and run the Java web and PostgreSQL database containers by executing this command:
```
$ docker-compose up -d
```

3.  Navigate to base URL 
     - http://localhost:8080
     - You should see the text "Capstone API"
     <br/>&nbsp;

4. View local Swagger API documentation
     - http://localhost:8080/swagger-ui.html
     <br/>&nbsp;

5. Stop and remove containers
```
$ docker-compose down
```

---

Note: User sign up and login endpoints require `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` values in [`docker-compose.yml`](https://github.com/mike-osu/capstone-api/blob/main/docker-compose.yml) 