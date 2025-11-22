# ---------- BUILD STAGE ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# copy pom for caching
COPY pom.xml .
# copy source
COPY src ./src

# build the jar
RUN mvn -B -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
