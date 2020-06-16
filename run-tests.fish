#!/usr/bin/env fish

set -x JAVA_HOME /usr/lib/jvm/java-8-openjdk
set -x PATH $JAVA_HOME/bin $PATH
# mvn test --file pom.xml
# mvn -X test --file pom.xml
mvn -e test --file pom.xml
