# OSTERREICH

[![Build Status](https://travis-ci.org/s61-austria/kontofahren.svg?branch=master)](https://travis-ci.org/s61-austria/kontofahren)

## Setup

### Docker

Docker is required to run most of the services. Almost everything has been prepared. To get started with this do the following

```docker-compose up```

This creates the services and sets up the necessary volumes. Now we need to add mysql to payara.

Find out where payara is mounted with `docker volume ls` and `docker volume inspect`

Download the [mysql](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.45.tar.gz) connector and unzip the jar to the payara volume, specifically in `glassfish/lib`
