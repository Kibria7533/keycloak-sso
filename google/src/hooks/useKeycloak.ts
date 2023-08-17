import axios from "axios";
import {clearLoginInfo, CONST_ID_TOKEN, getIdToken} from "../utils/storage";
import {
    KEYCLOAK_CLIENT_ID, KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_HOST, KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI,
    KEYCLOAK_REALM,
    KEYCLOAK_REDIRECT_URI,
    KEYCLOAK_TOKEN_URL
} from "../utils/keycloak";

export const axiosInstance = axios.create()
// headers: {
//     Accept: 'application/json',
//     "Content-Type": "application/json",
// },


export const getSSOLogoutURL = () => {
    let logOutUrl = `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/logout`;
    let url =
        logOutUrl +
        '?client_id=' +
        encodeURIComponent(KEYCLOAK_CLIENT_ID) +
        '&post_logout_redirect_uri=' +
        encodeURIComponent(KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI.toString() as any);

    if (getIdToken()) {
        url += '&id_token_hint=' +  encodeURIComponent(getIdToken() as any);
    }

    clearLoginInfo()

    return url;
};

// export const getKeycloakToken = async (authorizationCode: any): Promise<any> => {
//     return axiosInstance.post(KEYCLOAK_TOKEN_URL, {}, {
//         method: "POST",
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded',
//         },
//         params: {
//             grant_type: 'authorization_code',
//             client_id: KEYCLOAK_CLIENT_ID,
//             client_secret: KEYCLOAK_CLIENT_SECRET,
//             redirect_uri: encodeURI(KEYCLOAK_REDIRECT_URI),
//             code: authorizationCode,
//         }
//     });
// }


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




// export const getSSOLoginURL = () => {
//     return `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/auth?` +
//         `response_type=code&client_id=${KEYCLOAK_CLIENT_ID}&scope=openid&redirect_uri=${encodeURI(KEYCLOAK_REDIRECT_URI)}`;
// }

// export const keycloakLogin = async (redirectUrl: any, authorizationCode: any): Promise<any> => {
//     return axiosInstance.post( KEYCLOAK_TOKEN_URL, {
//         redirect_uri: encodeURI(redirectUrl.toString()),
//         code: authorizationCode
//     });
// }