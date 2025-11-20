# JDK 17 / 21 중 네 프로젝트에 맞게 선택
FROM eclipse-temurin:17-jre-alpine

# 컨테이너 타임존 (옵션)
ENV TZ=Asia/Seoul

# 앱이 사용하는 포트
EXPOSE 8080

WORKDIR /app

# Gradle 빌드 기준: build/libs/*.jar
# Maven이면 target/*.jar로 바꿔라.
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Spring Boot에서 .env를 읽게 하려면, 보통 env_file로 환경변수로 들어가므로
# 별도 처리는 필요 없다. (docker-compose에서 env_file 사용)

ENTRYPOINT ["java", "-jar", "app.jar"]
