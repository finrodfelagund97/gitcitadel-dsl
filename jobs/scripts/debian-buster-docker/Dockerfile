# syntax=docker/dockerfile:1

FROM debian:buster

# install build tools
RUN apt-get update
RUN apt-get -y install cmake
RUN apt-get -y install build-essential
RUN apt-get -y install git

# install python3
RUN apt-get install -y python3 python3-pip

# create slavehost user
RUN useradd -ms /bin/bash slavehost
RUN usermod -aG sudo slavehost

# set working directory to the home of this new user
WORKDIR /home/slavehost

# fetch the 2024.07.12 version
RUN git clone -b 2024.07.12 https://github.com/microsoft/vcpkg.git

# change user to slavehost
USER slavehost