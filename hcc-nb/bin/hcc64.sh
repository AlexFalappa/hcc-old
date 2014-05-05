#!/bin/bash

#launch jvm with natives in linux64 folder
java -Xms128m -Dsun.java2d.noddraw=true -Djava.library.path=lib/linux64 -jar hcc.jar
