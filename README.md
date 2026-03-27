# User Service

## Overview
// TODO

[//]: # (TODO)

## Tech Stack
- Java
- Spring boot
- Postgres DB
- Swagger

## Local setup

We use containers for local setup and these are managed by compose file.
```bash
podman-compose up -d
```

Start the spring-boot application

```bash
./mvnw spring-boot:run
```

### Connecting to Postgres DB
Use this connection details to connect to the postgres container

To check if the container is running, run the following command and the output should list the container

```bash
podman container ls | grep user-service-db
```

**HOST** - localhost

**PORT** - 5432

**USERNAME** - root

**PASSWORD** - password

**DB** - user-service