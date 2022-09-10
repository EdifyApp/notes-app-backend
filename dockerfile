FROM tomcat:9.0
COPY firebase-auth-credentials.json /data/
ENV GOOGLE_APPLICATION_CREDENTIALS=/data/firebase-auth-credentials.json
ADD build/libs/notes-api.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]