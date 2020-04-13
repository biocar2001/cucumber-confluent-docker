# Word Count con KStreams
kstreams + TDD + Docker + Docker-compose + Cucumber


Compilacion y package:
----------------------
mv clean install

Package en Dockerfile:
----------------------
docker build .

./kafka-console-producer \
    --broker-list localhost:9092 \
    --topic word-count-input