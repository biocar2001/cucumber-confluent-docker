#!/usr/bin/env bash
#abort on errors - TODO Hacer rebuild de la imagen docker o montar volumen en compose para el jar
set -xe
docker-compose -f src/test/resources/docker/docker-compose.yml up -d
until $(curl --output /dev/null --silent --head --fail http://localhost:8082/topics); do
    printf 'Waiting Kafka UP'
    sleep 5
done
printf 'Environment confluent UP'