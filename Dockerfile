# wait-for-it:  https://github.com/vishnubob/wait-for-it

FROM openjdk:21-jdk
ARG JAR_FILE=target/courier-tracking-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
VOLUME /tmp
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["sh", "-c", "/wait-for-it.sh courier-postgis:5432 -- java -jar application.jar"]
EXPOSE 8080
