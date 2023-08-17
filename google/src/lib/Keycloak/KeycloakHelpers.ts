import axios from "axios";


const KEYCLOAK_HOST = process.env.REACT_APP_KEYCLOAK_HOST;
const KEYCLOAK_REALM = process.env.REACT_APP_KEYCLOAK_REALM;
const KEYCLOAK_CLIENT_ID = process.env.REACT_APP_KEYCLOAK_CLIENT_ID || '';
const KEYCLOAK_REDIRECT_URI = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || 'http://localhost:3000/';

export const getSSOLoginURL = () => {
    return `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/auth?` +
        `response_type=code&client_id=${KEYCLOAK_CLIENT_ID}&scope=openid&redirect_uri=${encodeURI(KEYCLOAK_REDIRECT_URI)}`;
}

export const getSSOLogoutURL = () => {
    let logOutUrl = `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/logout`;
    let url =
        logOutUrl +
        '?client_id=' +
        encodeURIComponent(KEYCLOAK_CLIENT_ID) +
        '&post_logout_redirect_uri=' +
        encodeURIComponent(KEYCLOAK_REDIRECT_URI.toString() || '');

    if (localStorage.get("id_token")) {
        url +=
            '&id_token_hint=' +
            encodeURIComponent(localStorage.get("id_token") || '');
    }


    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('id_token');

    return url;
};


export const getSSOTokenURL = async (redirectUrl: any, authorizationCode: any): Promise<any> => {
    try {
        const expirationDate = new Date();
        expirationDate.setDate(expirationDate.getDate() + 7);

        const {data} = await axios.post(
            'http://localhost:5000/public/back-channel/keycloak-auth-token',
            {
                redirect_uri: encodeURI(redirectUrl.toString()),
                code: authorizationCode,
            }
        );
        return data
    } catch (error) {
        console.error('Error fetching auth token:', error);
    }
}
