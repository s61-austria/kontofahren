#!/bin/sh

gradle assemble
cp build/libs/government.war government-docker
docker-compose build
echo "Copied new artifact. Run it with `docker-compose up`" 
