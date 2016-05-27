lyrics-engine
=============

[![Join the chat at https://gitter.im/scalalab3/lyrics-engine](https://badges.gitter.im/scalalab3/lyrics-engine.svg)](https://gitter.im/scalalab3/lyrics-engine?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/scalalab3/lyrics-engine.svg?branch=master)](https://travis-ci.org/scalalab3/lyrics-engine)


About
-----

This is a lyrics engine application. Utility to discover new songs you might like, based on your favorite songs lyrics.


Development
-----------

To bootstrap development process, there is a prepared environment setup. 
It is based on Vagrant Ubuntu image with all the required tools in place, such as Java, Scala, mongodb and etc., set up by Ansible.
Please make sure you have Vagrant [installed](https://www.vagrantup.com/downloads.html) on your computer.

To initialize the environment run:
```bash
vagrant up dev
vagrant ssh dev
```

The first run might take some time, while all the packages are installed. Subsequent runs should be much faster.

All environment configurations is done using Ansible. All future updates should be reflected there, to maintain the *immutable container* state.

In case some updates to configuration were made, you can run `ansible-playbook` script from within the virtual machine or, alternatively, you can pass `--provision` flag to Vagrant.
If you don't have the `dev` virtual machine running yet it would be:
```bash
vagrant up dev --provision
```
or, if you already have it running:
```bash
vagrant reload dev --provision
```

For more information check [Vagrant](https://www.vagrantup.com/docs/) and [Ansible](http://docs.ansible.com) documentations.
 

Build and Deployment
--------------------

### SBT

Application is built using `sbt` and `sbt-native-packager` plugin.

To build it run:
```bash
sbt stage
```
The resulting application will be placed in *target/universal/stage/* directory.

When testing or running the application, you need to make sure that all the required dependencies (mongo and etc.) are in place, up and running.

To run the application execute:
```bash
sbt run
```

By default it will start on port *8080*, which can be overridden by defining appropriate `$PORT` environment variable.

While testing, in order to distinguish between tests that don't require third-party dependencies (unit tests) and tests that do (integration test), there is a convention to name them `*Spec` and `*Integ` respectively.
The first ones can be run using sbt only and are usually much faster.

To test the application you can run any of the following commands:
```bash
sbt "testOnly *Spec"    # to run only unit tests
sbt "testOnly *Integ"   # to run only integration tests
sbt test                # to run all the tests
```


### Docker

In order to simplify deployment and integration processes, Docker containers are used.
Two separate containers, one for deployment and one for testing, are defined. Container management is done using Docker Compose.
*(Please note that depending on your environment setup some of the examples below might have to be run as `sudo`)*

To run the application execute:
```bash
docker-compose up --build -d app
```
Here, `--build` means that the container image needs to be rebuilt before run. Flag `-d` means that it should run in *detached* mode.
 
To test the application you can run any of the following commands: 
```bash
docker-compose up --build unitTests     # to run only unit tests
docker-compose up --build integTests    # to run only integration tests
docker-compose up --build tests         # to run all the tests
```

To stop all the running containers you can simply execute:
```bash
docker-compose down
```

For more information on how to work with Docker please see [Docker Docs](https://docs.docker.com).
