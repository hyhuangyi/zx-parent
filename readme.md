## zx-parent 
* 版本 springBoot 2.1.9
* 打包 mvn install -Dmaven.test.skip=true -o
* 打jar到maven库 mvn install:install-file  -Dfile=D:/ftp4j-1.7.2.jar  -DgroupId=ftp4j -DartifactId=ftp4j -Dversion=1.7.2 -Dpackaging=jar