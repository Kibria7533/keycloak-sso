export const CONST_ACCESS_TOKEN = 'access_token';
export const CONST_REFRESH_TOKEN = 'refresh_token';
export const CONST_ID_TOKEN = 'id_token';

export const setAccessToken = (token: string): void => {
    token && localStorage.setItem(CONST_ACCESS_TOKEN, token);
}
export const getAccessToken = (): string | null => {
    return localStorage.getItem(CONST_ACCESS_TOKEN);
}

export const setRefreshToken = (token: string): void => {
    token && localStorage.setItem(CONST_REFRESH_TOKEN, token);
}
export const getRefreshToken = (): string | null => {
    return localStorage.getItem(CONST_REFRESH_TOKEN);
}

export const setIdToken = (token: string): void => {
    token && localStorage.setItem(CONST_ID_TOKEN, token);
}
export const getIdToken = (): string | null => {
    return localStorage.getItem(CONST_ID_TOKEN);
}

export const clearLoginInfo = () : void => {
    localStorage.removeItem(CONST_ACCESS_TOKEN)
    localStorage.removeItem(CONST_REFRESH_TOKEN)
    localStorage.removeItem(CONST_ID_TOKEN)
}