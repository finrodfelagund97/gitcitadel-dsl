#! /bin/bash
export SCRIPTPATH=$(dirname $(readlink -f ${BASH_SOURCE[0]}))

docker build -t gitcitadel-ubuntu-noble -f $SCRIPTPATH/Dockerfile .
docker tag gitcitadel-ubuntu-noble docker.gitcitadel.eu/gitcitadel-ubuntu-noble:latest
docker push docker.gitcitadel.eu/gitcitadel-ubuntu-noble:latest