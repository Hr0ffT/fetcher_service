FROM openjdk:latest


COPY target/fetcher_service-1.0-SNAPSHOT-jar-with-dependencies.jar fetcher_service-1.0-SNAPSHOT-jar-with-dependencies.jar


ENTRYPOINT ["java", "-jar", "fetcher_service-1.0-SNAPSHOT-jar-with-dependencies.jar", "-Xmx300m", "-Xms300m"]


