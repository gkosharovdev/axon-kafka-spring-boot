FROM openjdk:11.0-jre-sid

COPY ./target/shopping-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTIONS -jar order-service-0.0.1-SNAPSHOT.jar"]
