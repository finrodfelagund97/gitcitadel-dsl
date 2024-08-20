#! /bin/bash

export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker build -t gitcitadel-debian-bookworm -f $SCRIPTPATH/Dockerfile .
docker tag gitcitadel-debian-bookworm docker.gitcitadel.eu/gitcitadel-debian-bookworm:latest
docker push docker.gitcitadel.eu/gitcitadel-debian-bookworm:latest