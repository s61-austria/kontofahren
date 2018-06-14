#!/bin/sh

gradle assemble
cp build/libs/government.war government-docker
echo "Copied new artifact. Run it with `docker-compose up`" 
