import axios from "axios";
import {clearLoginInfo, getIdToken} from "../utils/storage";
import {
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_HOST,
    KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI,
    KEYCLOAK_REALM,
    KEYCLOAK_TOKEN_URL
} from "../utils/keycloak-urls";

export const axiosInstance = axios.create({
    headers: {
        Accept: 'application/json',
        "Content-Type": "application/json",
    }
})


export const getSSOLogoutURL = () => {
    let logOutUrl = `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/logout`;
    let url =
        logOutUrl +
        '?client_id=' +
        encodeURIComponent(KEYCLOAK_CLIENT_ID) +
        '&post_logout_redirect_uri=' +
        encodeURIComponent(KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI.toString() as any);

    if (getIdToken()) {
        url += '&id_token_hint=' + encodeURIComponent(getIdToken() as any);
    }

    clearLoginInfo()

    return url;
};

export const getKeycloakToken = async (values: any): Promise<any> => {
    return axiosInstance.post(KEYCLOAK_TOKEN_URL, values, {
        method: "POST",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    });
}

//
// export const getSSOTokenURL = async (redirectUrl: any, authorizationCode: any): Promise<any> => {
//     try {
//         const expirationDate = new Date();
//         expirationDate.setDate(expirationDate.getDate() + 7);
//
//         console.log(KEYCLOAK_TOKEN_URL)
//         console.log(KEYCLOAK_TOKEN_URL == "http://localhost:5000/auth/token")
//
//         const {data} = await axios.post(
//             KEYCLOAK_TOKEN_URL,
//             {
//                 redirect_uri: encodeURI(redirectUrl.toString()),
//                 code: authorizationCode,
//             }
//         );
//         return data
//     } catch (error) {
//         console.error('Error fetching auth token:', error);
//     }
// }


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