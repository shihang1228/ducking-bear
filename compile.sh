#!/bin/sh

javac -encoding UTF-8 -classpath $CATALINA_HOME/lib/servlet-api.jar -d ./target/classes/ ./src/main/java/com/bodejidi/Management.java
