import {config} from "dotenv";

config()
export const ENV = process.env;

export const KEYCLOAK_HOST_URL=process.env.KEYCLOAK_HOST
export const KEYCLOAK_REALM=process.env.KEYCLOAK_REALM
export const KEYCLOAK_CLIENT_ID=process.env.KEYCLOAK_CLIENT_ID
export const KEYCLOAK_CLIENT_SECRET=process.env.KEYCLOAK_CLIENT_SECRET
export const KEYCLOAK_ADMIN_PASSWORD=process.env.KEYCLOAK_ADMIN_PASSWORD
export const KEYCLOAK_ADMIN_USERNAME=process.env.KEYCLOAK_ADMIN_USERNAME
export const KEYCLOAK_PUBLIC_KEY=`-----BEGIN PUBLIC KEY-----\n${process.env.KEYCLOAK_PUBLIC_KEY}\n-----END PUBLIC KEY-----`

export const KEYCLOAK_URL_GET_TOKEN= KEYCLOAK_HOST_URL + "/realms/{realm_name}/protocol/openid-connect/token"
export const KEYCLOAK_USER_URL= KEYCLOAK_HOST_URL + "/admin/realms/{realm_name}/users"
export const KEYCLOAK_GET_GROUPS_REALM_LEVEL_ROLES=KEYCLOAK_HOST_URL+"/admin/realms/"+KEYCLOAK_REALM+"/groups/{GROUP_ID_FROM_API}/role-mappings/realm"
export const KEYCLOAK_GET_ALL_GROUPS=KEYCLOAK_HOST_URL+"/admin/realms/{realm_name}/groups"

export const KEYCLOAK_GET_GROUP_ROLES=KEYCLOAK_HOST_URL+"/admin/realms/"+KEYCLOAK_REALM+"/groups/{GROUP_ID}/role-mappings"

export const  KEYCLOAK_DEFAULT_USER_ROLE="Users Group"
export const  KEYCLOAK_CREATE_USER_PASSWORD_TYPE="password"