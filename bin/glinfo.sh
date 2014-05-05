#!/bin/bash

#setup classpath
CLASSPATH=..
CLASSPATH=$CLASSPATH:../classes
CLASSPATH=$CLASSPATH:../lib/worldwind.jar
CLASSPATH=$CLASSPATH:../lib/jogl.jar
CLASSPATH=$CLASSPATH:../lib/gluegen-rt.jar
CLASSPATH=$CLASSPATH:../lib/gdal.jar

#launch jvm
java -Xmx512m -Dsun.java2d.noddraw=true -Djava.library.path=../lib/linux -cp $CLASSPATH gov.nasa.worldwindx.examples.WorldWindDiagnostics
