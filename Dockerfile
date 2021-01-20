FROM openjdk:latest


#ENV _JAVA_OPTIONS="-Xmx300m -Xms300m"
COPY target/myjar.jar myjar.jar


ENTRYPOINT ["java", "-jar", "myjar.jar", "-Xmx300m", "-Xms300m"]


