FROM openjdk:17

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} iaccount-service.jar

ENTRYPOINT ["java","-jar","iaccount-service.jar"]

EXPOSE 8083