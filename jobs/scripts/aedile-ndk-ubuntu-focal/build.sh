#! /bin/bash
export SCRIPTPATH=$(dirname $(readlink -f ${0}))
echo "Hello $SCRIPTPATH"