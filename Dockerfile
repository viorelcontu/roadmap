FROM adoptopenjdk/openjdk11:alpine-jre

ARG VERSION
ARG LOCATION

COPY ["${LOCATION}/roadmap-${VERSION}.jar", "/opt/roadmap.jar"]

COPY ["docker/entrypoint.sh", "/"]

RUN set -x && \
    addgroup -S -g 2020 roadmap && \
    adduser -S -G roadmap -u 2020 roadmap && \
    chmod a+x "/entrypoint.sh" && \
    sed -i "s/\r//g" /entrypoint.sh

EXPOSE 8080

CMD ["default"]

ENTRYPOINT ["/entrypoint.sh"]

USER roadmap:roadmap