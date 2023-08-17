import Keycloak from "keycloak-js";

const keycloakConfig: Keycloak.KeycloakConfig = {
    url: 'http://localhost:7020/',
    realm: 'google',
    clientId: 'google-cli',
};

const client = new Keycloak(keycloakConfig);

export default client;
