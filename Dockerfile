FROM jboss/wildfly
ADD target/vsal.war /opt/jboss/wildfly/standalone/deployments/
