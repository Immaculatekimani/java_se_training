FROM openjdk:11
EXPOSE 8080
ADD target/java_se_training-1.0-SNAPSHOT.jar java_se_training-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/java_se_training-1.0-SNAPSHOT.jar"]