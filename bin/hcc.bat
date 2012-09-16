rem Windows batch file for running HMA Catalogue Client

rem setup classpath
set CLASSPATH=..
set CLASSPATH=%CLASSPATH%;..\classes
set CLASSPATH=%CLASSPATH%;..\lib\jcalendar-1.4.jar
set CLASSPATH=%CLASSPATH%;..\lib\worldwind.jar
set CLASSPATH=%CLASSPATH%;..\lib\jogl.jar
set CLASSPATH=%CLASSPATH%;..\lib\gluegen-rt.jar
set CLASSPATH=%CLASSPATH%;..\lib\gdal.jar
set CLASSPATH=%CLASSPATH%;..\lib\gdaldata.jar
set CLASSPATH=%CLASSPATH%;..\lib\catalogue-ws-client.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\activation-1.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axiom-api-1.2.12.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axiom-dom-1.2.12.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axiom-impl-1.2.12.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-adb-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-clustering-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-corba-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-fastinfoset-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-jaxws-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-kernel-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-metadata-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-mtompolicy-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-saaj-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-soapmonitor-servlet-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-transport-http-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-transport-local-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\axis2-xmlbeans-1.6.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\bcel-5.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-cli-1.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-codec-1.3.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-fileupload-1.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-httpclient-3.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-io-1.4.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\commons-logging-1.1.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\httpcore-4.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\jalopy-1.5rc3.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\jaxen-1.1.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\jaxws-tools-2.1.3.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\jettison-1.0-RC2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\jsr311-api-1.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\juli-6.0.16.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\log4j-1.2.15.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\mail-1.4.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\mex-1.6.1-impl.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\neethi-3.0.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\regexp-1.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\saxon8-dom.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\saxon8.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\tribes-6.0.16.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\woden-api-1.0M9.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\woden-impl-commons-1.0M9.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\woden-impl-dom-1.0M9.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\wsdl4j-1.6.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\wstx-asl-3.2.9.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\xalan-2.7.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\xbean_xpath.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\xmlbeans-2.3.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\xml-resolver-1.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis2\XmlSchema-1.4.7.jar

rem launch jvm with natives in windows folder
java -Xmx512m -Dsun.java2d.noddraw=true -Djava.library.path=..\lib\windows -cp %CLASSPATH% main.App
