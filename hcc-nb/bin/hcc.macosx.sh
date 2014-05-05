#!/bin/bash

#launch jvm with natives in macosx folder
java -Xms128m -Dsun.java2d.noddraw=true -Djava.library.path=lib/macosx -jar hcc.jar
