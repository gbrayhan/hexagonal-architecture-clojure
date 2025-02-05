#!/bin/bash
set -e

echo "Waiting for PostgreSQL to be available..."
while ! nc -z db 5432; do
  sleep 0.1
done
echo "PostgreSQL is available."

echo "Running migrations..."
java -jar clojure-ddd-hexagonal.jar migrate

echo "Starting the application..."
java -jar clojure-ddd-hexagonal.jar
