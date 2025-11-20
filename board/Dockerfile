# 1. 런타임 이미지 (JDK 17)
FROM eclipse-temurin:17-jdk-alpine

# 2. JAR 파일 경로 (Gradle 기준)
#   - ./gradlew bootJar 실행 시 build/libs/ 아래에 생성됨
ARG JAR_FILE=build/libs/*.jar

# 3. 컨테이너 안으로 JAR 복사
COPY ${JAR_FILE} app.jar

# 4. 컨테이너에서 열어줄 포트
EXPOSE 8080

# 5. 애플리케이션 실행
#    - 필요시 docker run 할 때 -e JAVA_OPTS로 옵션 전달 가능
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS:-'-Xms256m -Xmx512m'} -jar /app.jar"]
