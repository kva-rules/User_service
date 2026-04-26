FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy pre-built JAR (build locally first via services.sh build or mvn package)
COPY target/User_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENV SPRING_PROFILES_ACTIVE=local
ENV JAVA_OPTS="-Xms256m -Xmx512m"


ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
