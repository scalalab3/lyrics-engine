#!/bin/bash

set -e

if [ "$TEST_TYPE" = "unit" ]
then
    sbt "testOnly *Spec"
elif [ "$TEST_TYPE" = "integ" ]
then
    sbt "testOnly *Integ"
elif [ "$TEST_TYPE" = "all" ]
then
    sbt test
fi
