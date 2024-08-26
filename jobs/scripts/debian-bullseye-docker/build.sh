#! /bin/bash

export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker build -t gitcitadel-debian-bullseye -f $SCRIPTPATH/Dockerfile .
docker tag gitcitadel-debian-bullseye docker.gitcitadel.eu/gitcitadel-debian-bullseye:latest
docker push docker.gitcitadel.eu/gitcitadel-debian-bullseye:latest