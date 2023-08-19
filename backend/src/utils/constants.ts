import {config} from "dotenv";

config()
export const  KEYCLOAK_HOST_URL=process.env.APP_KEYCLOAK_HOST
export const  KEYCLOAK_REALM=process.env.APP_KEYCLOAK_REALM
export const KEYCLOAK_CLIENT_ID=process.env.APP_KEYCLOAK_CLIENT_ID
export const KEYCLOAK_CLIENT_SECRET=process.env.APP_KEYCLOAK_CLIENT_SECRET
export const KEYCLOAK_ADMIN_PASSWORD=process.env.KEYCLOAK_ADMIN_USERNAME
export const KEYCLOAK_ADMIN_USERNAME=process.env.KEYCLOAK_ADMIN_PASSWORD
export const KEYCLOAK_PUBLIC_KEY=`-----BEGIN PUBLIC KEY-----\n${process.env.APP_KEYCLOAK_PUBLIC_KEY}\n-----END PUBLIC KEY-----`
