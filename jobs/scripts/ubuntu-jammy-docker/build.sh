#! /bin/bash
export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))


docker build -t gitcitadel-ubuntu-jammy -f $SCRIPTPATH/Dockerfile
docker tag gitcitadel-ubuntu-jammy docker.gitcitadel.eu/gitcitadel-ubuntu-jammy:latest
docker push docker.gitcitadel.eu/gitcitadel-jammy-noble:latest