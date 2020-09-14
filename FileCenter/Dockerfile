FROM 100.125.0.198:20202/hwcse/dockerhub-java:8-jre-alpine
RUN mkdir -p /home/apps/server
COPY target/file-center-0.0.1-SNAPSHOT.jar /home/apps/server
WORKDIR /home/apps/server/
ENTRYPOINT ["java", "-jar", "file-center-0.0.1-SNAPSHOT.jar"]
