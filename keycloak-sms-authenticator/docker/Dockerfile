FROM bitnami/keycloak:18.0.2

COPY keycloak-sms-authenticator-ng.tar.gz /opt/bitnami/keycloak

RUN cd /opt/bitnami/keycloak && \
    tar -xzf keycloak-sms-authenticator-ng.tar.gz && \
    rm keycloak-sms-authenticator-ng.tar.gz
