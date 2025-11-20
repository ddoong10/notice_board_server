# 1단계: 빌드 (Gradle + JDK 17)
FROM gradle:8.10.2-jdk17-alpine AS builder

WORKDIR /workspace

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 런타임 (JRE 17)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

# ★ 여기 수정: /app.jar 가 아니라 app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
