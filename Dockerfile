# 1) Compile java archive
FROM clojure AS builder
WORKDIR /app
COPY . .
RUN lein ring uberjar

# 2) Run java archive
FROM openjdk:26-slim
WORKDIR /app
RUN apt-get update && apt-get install -y \
    curl \
    libssl3 \
    libgraphite2-3 \
    ca-certificates
RUN curl --proto '=https' --tlsv1.2 -fsSL https://drop-sh.fullyjustified.net |sh \
    && mv ./tectonic /usr/local/bin
COPY --from=builder /app/target/cv-generator-1.0.0-standalone.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
