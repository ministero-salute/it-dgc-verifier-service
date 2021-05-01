FROM adoptopenjdk:11-jre-hotspot

# switch to root user
USER root

# Metadata
LABEL module.name="it-eucert-verifier-service" \
      module.version="0.0.1-SNAPSHOT"

WORKDIR /
ADD . /it-eucert-verifier-service
WORKDIR /it-eucert-verifier-service


COPY [ "target/it-eucert-verifier-service-0.0.1-SNAPSHOT.jar", "/it-eucert-verifier-service/app.jar" ]

ENV JAVA_OPTS="$JAVA_OPTS -Xms256M -Xmx1G"

EXPOSE 8080

RUN useradd \
        --no-log-init \
        --home /it-eucert-verifier-service \
        --shell /bin/bash \
        gdc \
    && chown --recursive uecert:root /it-eucert-verifier-service \   
    && chmod -R g+rwx /it-eucert-verifier-service
USER eucert


ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /it-eucert-verifier-service/app.jar --spring.config.location=file:/it-eucert-verifier-service/config/application.properties" ]
