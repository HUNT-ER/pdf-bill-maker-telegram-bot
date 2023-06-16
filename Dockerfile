FROM eclipse-temurin:17-jre
ARG APP_JAR=target/*.jar
COPY ${APP_JAR} app.jar
CMD ["java", "-jar", "app.jar"]