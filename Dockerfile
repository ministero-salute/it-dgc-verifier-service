FROM adoptopenjdk:11-jre-hotspot

# switch to root user
USER root

# Metadata
LABEL module.name="it-dgc-verifier-service" \
      module.version="0.0.1-SNAPSHOT"

WORKDIR /
ADD . /it-dgc-verifier-service
WORKDIR /it-dgc-verifier-service


COPY [ "target/it-dgc-verifier-service-0.0.1-SNAPSHOT.jar", "/it-dgc-verifier-service/app.jar" ]

ENV JAVA_OPTS="$JAVA_OPTS -Xms256M -Xmx1G"

EXPOSE 8080

RUN useradd \
        --no-log-init \
        --home /it-dgc-verifier-service \
        --shell /bin/bash \
        gdc \
    && chown --recursive dgc:root /it-dgc-verifier-service \   
    && chmod -R g+rwx /it-dgc-verifier-service
USER dgc


ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /it-dgc-verifier-service/app.jar --spring.config.location=file:/it-dgc-verifier-service/config/application.properties" ]
