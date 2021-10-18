FROM gradle:6.8.0-jdk11-hotspot
COPY --chown=gradle:gradle . /home/application/
WORKDIR /home/application/
RUN chmod +x gradlew
ENTRYPOINT  ["./gradlew", "bootRun"]