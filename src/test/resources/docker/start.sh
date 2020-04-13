#!/usr/bin/env bash
#abort on errors
set -xe
docker-compose -f src/test/resources/docker/docker-compose.yml up -d
