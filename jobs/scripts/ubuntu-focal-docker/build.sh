#! /bin/bash

export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker build -t gitcitadel-ubuntu-focal -f $SCRIPTPATH/Dockerfile
docker tag gitcitadel-ubuntu-focal docker.gitcitadel.eu/gitcitadel-ubuntu-focal:latest
docker push docker.gitcitadel.eu/gitcitadel-ubuntu-focal:latest