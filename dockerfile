FROM tomcat:9.0
ADD build/libs/notes-api.war /usr/local/tomcat/webapps
COPY firebase-auth-credentials.json /data/
ENV GOOGLE_APPLICATION_CREDENTIALS=/data/firebase-auth-credentials.json
EXPOSE 8080
CMD ["catalina.sh", "run"]