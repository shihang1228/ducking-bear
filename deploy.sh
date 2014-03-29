#!/bin/sh
mkdir -p $CATALINA_HOME/webapps/bear/
cp -r ./src/main/webapps/* $CATALINA_HOME/webapps/bear/
cp -r ./target/classes $CATALINA_HOME/webapps/bear/WEB-INF
cp -r ./lib $CATALINA_HOME/webapps/bear/WEB-INF/
