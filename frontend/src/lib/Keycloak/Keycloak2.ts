'use client'

import Keycloak from 'keycloak-js';

const keycloakConfig = {
    url: 'http://localhost:8080/',
    realm: 'myrealm',
    clientId: 'myclient',
    // redirectUri: 'http://localhost:3000'

};

const keycloak2 = new Keycloak(keycloakConfig);

export default keycloak2;