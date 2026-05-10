FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/notificationservice.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "notificationservice.jar"]