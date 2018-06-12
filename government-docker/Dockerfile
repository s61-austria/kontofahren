FROM jboss/wildfly
ADD mysql-connector.jar /opt/jboss/wildfly/standalone/deployments/
ADD government.war /opt/jboss/wildfly/standalone/deployments/
ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml","-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
