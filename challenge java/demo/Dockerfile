FROM openjdk:21-jdk-slim

ARG APP_USER=appuser
ARG APP_GROUP=appgroup

RUN addgroup --system ${APP_GROUP} && \
    adduser --system --ingroup ${APP_GROUP} ${APP_USER}

WORKDIR /app

COPY target/mental_sos-0.0.1-SNAPSHOT.jar app.jar

USER ${APP_USER}

EXPOSE 8080

ENV APP_NAME="MentalSOS_API"
ENV APP_PROFILE="docker" 

ENTRYPOINT ["java", "-jar", "app.jar"]