FROM openjdk:11-jre
# Change to non-root privilege
USER 29000
WORKDIR /
COPY target/word-count-1.0-SNAPSHOT-jar-with-dependencies.jar /
CMD ["java", "-jar","word-count-1.0-SNAPSHOT-jar-with-dependencies.jar"]
