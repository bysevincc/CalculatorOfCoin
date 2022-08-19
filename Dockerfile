FROM openjdk:8-jdk-alpine
VOLUME /calculatorcoin
ARG JAR_FILE=./target/calculator-coin-1.0.0.jar
ADD ${JAR_FILE} calculator-coin-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/calculator-coiin-1.0.0.jar"]