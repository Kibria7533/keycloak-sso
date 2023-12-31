import axios from "axios";
import {clearLoginInfo, getIdToken} from "../utils/storage";
import {
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_HOST,
    KEYCLOAK_KEYCLOAK_LOGOUT_REDIRECT_URI,
    KEYCLOAK_PROFILE_URL,
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

export const getProfile = async (token: string): Promise<any> => {
    return axiosInstance.get(KEYCLOAK_PROFILE_URL, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            Authorization: `Bearer ${token}`
        },
    });
}