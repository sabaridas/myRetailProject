FROM openjdk:11
ADD ./target/myRetail-0.0.1-SNAPSHOT.jar /usr/src/myRetail-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "myRetail-0.0.1-SNAPSHOT.jar"]