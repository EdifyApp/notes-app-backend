FROM gradle:7.6.0-jdk11-alpine as gradle
WORKDIR /app
COPY src ./src
COPY settings.gradle ./
COPY build.gradle ./

RUN gradle bootWar

FROM tomcat:9.0
COPY firebase-auth-credentials.json /data/
ENV GOOGLE_APPLICATION_CREDENTIALS=/data/firebase-auth-credentials.json

WORKDIR /usr/local/tomcat/webapps/
ENV SPRING_PROFILES_ACTIVE=prod
COPY --from=gradle app/build/libs/notes-api.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]