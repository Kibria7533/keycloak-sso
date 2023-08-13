import Keycloak from "keycloak-js";

const keycloakConfig = {
    url: 'http://localhost:7020/',
    realm: 'google',
    clientId: 'google-cli',
    // redirectUri: 'http://localhost:3000'
};

const client= new Keycloak(keycloakConfig);

export default client