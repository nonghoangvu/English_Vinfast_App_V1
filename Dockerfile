FROM openjdk:22-jdk

COPY target/englishvinfastapp.jar englishvinfastapp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "englishvinfastapp.jar"]