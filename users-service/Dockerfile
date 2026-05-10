
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/usersservice.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "usersservice.jar"]

