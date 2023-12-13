FROM gradle:7.4-jdk11-alpine as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:17-ea-11-jdk-slim
# 공식 OpenJDK 17 이미지를 기반으로 함

# 작업 디렉토리 설정 (원하는 경로로 변경 가능)
WORKDIR /app

# JAR 파일을 컨테이너 내의 /app 디렉토리로 복사
COPY build/libs/Gateway-0.0.1-SNAPSHOT.jar /app/Gateway.jar

# 어플리케이션의 포트 (필요에 따라 수정)
EXPOSE 8500

# 어플리케이션 실행 (JAR 파일 이름에 따라 수정)
CMD ["java", "-jar", "/app/Gateway.jar"]