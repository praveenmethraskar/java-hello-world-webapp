FROM tomcat
LABEL maintainer address "praveen"
COPY **/*.war /usr/local/tomcat/webapps
CMD ["catalina.sh","run"]
EXPOSE 8080
