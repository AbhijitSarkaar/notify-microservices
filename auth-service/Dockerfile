
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/authservice.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "authservice.jar"]
