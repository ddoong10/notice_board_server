# 1단계: 빌드 (Gradle + JDK 17)
FROM gradle:8.10.2-jdk17-alpine AS builder

WORKDIR /workspace

# 프로젝트 전체 복사 (src, build.gradle, settings.gradle, gradle 폴더 등)
COPY . .

# Gradle로 JAR 빌드 (컨테이너 안에서는 JDK 17 사용)
RUN gradle clean bootJar --no-daemon

# 2단계: 런타임 (JRE 17)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 1단계에서 빌드된 JAR만 가져오기
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS:-'-Xms256m -Xmx512m'} -jar /app.jar"]
