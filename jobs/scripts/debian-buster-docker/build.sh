#! /bin/bash

export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker build -t gitcitadel-debian-buster -f $SCRIPTPATH/Dockerfile .
docker tag gitcitadel-debian-buster docker.gitcitadel.eu/gitcitadel-debian-buster:latest
docker push docker.gitcitadel.eu/gitcitadel-debian-buster:latest