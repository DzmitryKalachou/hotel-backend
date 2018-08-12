#!/usr/bin/env bash
set -e
docker-compose down
docker-compose run -d --service-ports --name hotel_db db
sleep 5  #wait for database initialization, it can be implemented in a better way with `psql` connection and check, but it requires `psql` on host machine
./gradlew clean build
docker build -t hotel-backend:latest -f src/main/docker/Dockerfile  build/libs
docker-compose run -d --service-ports --name hotel_backend --no-deps  app
