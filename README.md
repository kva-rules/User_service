# User Service

User profile + department management microservice. Reads `X-User-Id` / `X-User-Role` headers that the API Gateway injects after `auth-service` validates the JWT, then serves profile CRUD and department lookups.

## At a glance
| | |
|---|---|
| **Port** | 8082 |
| **Database** | postgres-user (`user_db`) |
| **Kafka topics (in)** | `user.registered` (from auth-service) |
| **Kafka topics (out)** | `user.profile-updated` |
| **Swagger UI (direct)** | http://localhost:8082/swagger-ui.html |
| **Swagger UI (via gateway)** | http://localhost:8080/swagger-ui.html?urls.primaryName=user-service |
| **OpenAPI JSON** | http://localhost:8082/v3/api-docs |
| **Java** | 21 (Temurin) |
| **Spring Boot** | 3.3.5 |

## What it does
- Stores user profiles (name, department, bio, avatar URL) — the `authUserId` ties back to auth_db
- Subscribes to `user.registered` from auth-service and creates a matching profile row
- Serves `/api/users/{id}` for the frontend's logged-in user view
- Department CRUD for admin UI

## API surface

### Users (`/api/users`)
| Method | Path | Auth | Purpose |
|---|---|---|---|
| POST | `/api/users` | Bearer JWT | Create a new user profile |
| GET | `/api/users/{id}` | Bearer JWT | Fetch a single user by UUID |
| PUT | `/api/users/{id}` | Bearer JWT | Update an existing user profile |
| GET | `/api/users` | Bearer JWT + `ROLE_ADMIN` | List every user (admin only) |

### Departments (`/api/departments`)
| Method | Path | Auth | Purpose |
|---|---|---|---|
| POST | `/api/departments` | Bearer JWT | Create a new department |

All endpoints expect the `Authorization: Bearer <token>` header when called through the API Gateway. Responses are wrapped in the shared `ApiResponse<T>` envelope from `com.kva:common-library`.

## Configuration
| Env var | Yaml key | Default | Purpose |
|---|---|---|---|
| `SERVER_PORT` | `server.port` | `8082` | HTTP listener |
| `SPRING_DATASOURCE_URL` | `spring.datasource.url` | `jdbc:postgresql://postgres-user:5432/user_db` | Postgres JDBC URL (container DNS) |
| `SPRING_DATASOURCE_USERNAME` | `spring.datasource.username` | `postgres` | DB user |
| `SPRING_DATASOURCE_PASSWORD` | `spring.datasource.password` | `postgres` | DB password |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `spring.jpa.hibernate.ddl-auto` | `update` | Schema strategy |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `spring.kafka.bootstrap-servers` | `kafka:9092` | Kafka broker list |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | `spring.kafka.consumer.group-id` | `user-service-group` | Consumer group |
| `JWT_SECRET` | `jwt.secret` | `myVerySecretKeyForJWTThatIsLongEnoughForHS512Signature` | Must match auth-service |

Activate the `local` profile (`SPRING_PROFILES_ACTIVE=local`) to point at `localhost` Postgres/Kafka instead of the container DNS names.

## Kafka events
- Consumes `user.registered` → creates empty profile linked to `authUserId`
- Produces `user.profile-updated` on PUT/PATCH operations

Consumer config uses `JsonDeserializer` with `spring.json.trusted.packages=*`, `auto-offset-reset=earliest`, and the `user-service-group` group ID. Producer uses `JsonSerializer` for values and `StringSerializer` for keys.

## Build & run
```bash
./services.sh start user-service
```
or
```bash
export JAVA_HOME=/opt/homebrew/Cellar/openjdk@21/21.0.11
cd User_service
mvn -DskipTests -Dmaven.test.skip=true spring-boot:run
```

## Docker
```bash
docker build -t user-service:latest .
docker run --rm -p 8082:8082 user-service:latest
```

## Kubernetes
- Manifest: `k8s/user-service.yaml` (part of `k8s/services.yaml`)
- Namespace: `ticketing-system`
- Service DNS (intra-cluster): `user-service:8082`
- Access via ingress: `http://ticketing.local/api/users/**`

```bash
# View logs
./services.sh k8s-logs user-service
# or: kubectl logs -n ticketing-system deployment/user-service -f

# Restart the pod
kubectl rollout restart deployment/user-service -n ticketing-system
```

## Troubleshooting

**GET /api/users returns 403**
The `@PreAuthorize("hasRole('ADMIN')")` on the list endpoint rejected your request. Your JWT needs `ROLE_ADMIN` — re-register through auth-service with an admin role or hit the role-assignment endpoint.

**GET /api/users/{id} returns 403**
Your JWT has empty roles. Re-register or hit auth-service to assign a role. The gateway will not inject `X-User-Role` if the token has none.

**GET /api/users returns 500**
Usually an empty database or a missing `authUserId` foreign key. Send a few POST /api/users first, or confirm the `user.registered` consumer is running.

**User profile not found / features that display names show "unknown user"**
`user_db.user_profiles` must contain a row with `auth_user_id` matching each UUID in `auth_db.users`. The service creates profiles automatically by consuming the `user.registered` Kafka event. If Kafka was down during seeding or the consumer group offset is stale, profiles will be absent. Fix: for each registered user, call `POST /api/users` with `{authUserId, email, name, role}` using the UUID from `auth_db.users.id`.

**Startup fails with `Unable to connect to Kafka`**
Check `SPRING_KAFKA_BOOTSTRAP_SERVERS`. Inside Docker Compose it must be `kafka:9092`; for bare-metal local runs switch to the `local` profile which targets `localhost:9092`.

## Tech stack
- Java 21
- Spring Boot 3.3.5
- Spring Security (JWT filter reading gateway-injected headers)
- Spring Data JPA + PostgreSQL
- Spring Kafka
- springdoc-openapi 2.6.0
- Lombok 1.18.34
- jjwt 0.12.6
- `com.kva:common-library` 1.0.0 (shared `ApiResponse<T>` envelope)
