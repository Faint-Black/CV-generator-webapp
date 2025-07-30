# 1) Compile java archive
FROM clojure AS builder
WORKDIR /app
COPY . .
RUN lein ring uberjar

# 2) Produce docker image
FROM openjdk
WORKDIR /app
COPY --from=builder /app/target/cv-generator-1.0.0-standalone.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
