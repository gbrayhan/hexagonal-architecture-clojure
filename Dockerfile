FROM clojure:lein-2.9.7 AS build

WORKDIR /app

COPY project.clj /app/
RUN lein deps

COPY . /app/

RUN lein uberjar && \
    echo "Contents of /app/target:" && \
    ls -l /app/target && \
    if [ ! -f /app/target/clojure-ddd-hexagonal.jar ]; then \
        echo "Error: The JAR file was not found in /app/target"; \
        exit 1; \
    fi

FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y netcat && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/target/*.jar /app/clojure-ddd-hexagonal.jar
COPY --from=build /app/migrations /app/migrations
COPY --from=build /app/resources/migratus.edn /app/resources/migratus.edn

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 3000

ENTRYPOINT ["./entrypoint.sh"]
