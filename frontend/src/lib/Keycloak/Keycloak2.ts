'use client'

import Keycloak from 'keycloak-js';

const keycloakConfig = {
    url: 'http://localhost:7020/',
    realm: 'google',
    clientId: 'google-cli',
    // redirectUri: 'http://localhost:3000'
};

const keycloak2 = new Keycloak(keycloakConfig);

export default keycloak2;