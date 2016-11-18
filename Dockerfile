from debian 
env DEBIAN_FRONTEND noninteractive
run sed -e 's/httpredir.debian.org/debian.mirrors.ovh.net/g' -i /etc/apt/sources.list
run apt-get update && \
    apt-get install -y maven openjdk-7-jdk && \
    apt-get clean 
add pom.xml /srv/jersey-skeleton/
add server/pom.xml        /srv/jersey-skeleton/server/
add common/pom.xml        /srv/jersey-skeleton/common/
add common-client/pom.xml /srv/jersey-skeleton/common-client/
add client-cli/pom.xml    /srv/jersey-skeleton/client-cli/
workdir /srv/jersey-skeleton/
run mvn install
add . /srv/jersey-skeleton/
expose 8080
cmd mvn jetty:run
