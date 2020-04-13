#!/usr/bin/env bash
#abort on errors
set -e
docker-compose -f src/test/resources/docker/docker-compose.yml down