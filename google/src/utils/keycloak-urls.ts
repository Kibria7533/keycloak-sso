export const KEYCLOAK_HOST = process.env.REACT_APP_KEYCLOAK_HOST;
export const KEYCLOAK_REALM = process.env.REACT_APP_KEYCLOAK_REALM;
export const KEYCLOAK_CLIENT_ID = process.env.REACT_APP_KEYCLOAK_CLIENT_ID || '';
export const KEYCLOAK_REDIRECT_URI = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || 'http://localhost:3000/';
export const KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI = process.env.REACT_APP_KEYCLOAK_LOGOUT_REDIRECT_URI || 'http://localhost:3000/';
export const BACKEND_BASE_URL = process.env.REACT_APP_BACKEND_BASE_URL;

// export const KEYCLOAK_TOKEN_URL: string = KEYCLOAK_HOST + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect/token"
export const KEYCLOAK_TOKEN_URL: string = BACKEND_BASE_URL + "/auth/token"
export const KEYCLOAK_PROFILE_URL: string = BACKEND_BASE_URL + "/auth/profile"

export const KEYCLOAK_LOGIN_URL: string = KEYCLOAK_HOST + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect/auth?" +
    "response_type=code&client_id="+ KEYCLOAK_CLIENT_ID+ "&scope=openid&redirect_uri=" + encodeURI(KEYCLOAK_REDIRECT_URI);
