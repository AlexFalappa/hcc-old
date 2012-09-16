REM Windows batch file for running HMA Catalogue Client

rem setup classpath
set CLASSPATH=..
set CLASSPATH=%CLASSPATH%;..\classes
set CLASSPATH=%CLASSPATH%;..\lib\worldwind.jar
set CLASSPATH=%CLASSPATH%;..\lib\jogl.jar
set CLASSPATH=%CLASSPATH%;..\lib\gluegen-rt.jar
set CLASSPATH=%CLASSPATH%;..\lib\gdal.jar

java -Xmx512m -Dsun.java2d.noddraw=true -Djava.library.path=..\lib\windows -cp %CLASSPATH% gov.nasa.worldwindx.examples.WorldWindDiagnostics
