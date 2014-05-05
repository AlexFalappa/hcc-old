#!/bin/bash

#launch jvm with natives in linux folder
java -Xms128m -Dsun.java2d.noddraw=true -Djava.library.path=lib/linux -jar hcc.jar
