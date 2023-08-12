export const keycloakConfigs: any = {
    local: {
        realm: 'myrealm',
        clientId: 'myclient',
        clientSecret: 'yfZP19xNuY7JUoC0D0BYSMzqIHwCjZNB',
        loginUrl:
            'http://localhost:8080/realms/myrealm/protocol/openid-connect/auth',
        logoutUrl:
            'http://localhost:8080/realms/myrealm/protocol/openid-connect/logout',
        passwordUpdateUrl:
            'http://localhost:8080/realms/myrealm/protocol/openid-connect/auth',
        tokenUrl:
            'http://localhost:8080/realms/myrealm/protocol/openid-connect/token',
        loginCallbackUri: '/callback',
        logoutCallbackUri: '/logout',
        updatePasswordCallback: '/callback-update-password',
    },

};