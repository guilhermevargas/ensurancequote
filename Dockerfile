FROM gradle:7.6-jdk17 AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/ensurancequote.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
