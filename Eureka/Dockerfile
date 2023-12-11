FROM openjdk:17-ea-11-jdk-slim
# 공식 OpenJDK 17 이미지를 기반으로 함

# 작업 디렉토리 설정 (원하는 경로로 변경 가능)
WORKDIR /app

# JAR 파일을 컨테이너 내의 /app 디렉토리로 복사
COPY build/libs/Eureka-0.0.1-SNAPSHOT.jar /app/Eureka.jar

# 어플리케이션의 포트 (필요에 따라 수정)
EXPOSE 8771

# 어플리케이션 실행 (JAR 파일 이름에 따라 수정)
CMD ["java", "-jar", "/app/Eureka.jar"]