FROM jboss/wildfly:latest
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin2 --silent
ADD target/vsal.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
