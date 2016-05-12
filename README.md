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

Application is built using `sbt` and `sbt-native-packager` plugin.

To build it run:
```bash
sbt stage
```
The resulting application will be placed in *target/universal/stage/* directory.

To test it run:
```bash
sbt test
```

To run the application execute:
```bash
sbt run
```
By default it will start on port *8080*, which can be overridden by defining appropriate `$PORT` environment variable.