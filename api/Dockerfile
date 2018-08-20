FROM openjdk:8-jdk-alpine
LABEL maintainer="leoguzmadiaz@g@gmail.com"

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"]