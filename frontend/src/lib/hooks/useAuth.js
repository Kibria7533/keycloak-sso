import React, { useState, useEffect, useRef } from "react";
import Keycloak from "keycloak-js";
import keycloak from "../Keycloak/Keycloak";
import client from "../Keycloak/Keycloak";

// const keycloakConfig = {
//     url: 'http://localhost:7020/',
//     realm: 'google',
//     clientId: 'google-cli',
//     // redirectUri: 'http://localhost:3000'
// };
//
// const client= new Keycloak(keycloakConfig);

const useAuth = () => {
    const isRun = useRef(false);
    const [token, setToken] = useState(null);
    const [isLogin, setLogin] = useState(false);

    useEffect(() => {
        if (isRun.current) return;

        isRun.current = true;
        client
            .init()
            .then((authenticated) => {
                setLogin(authenticated);

            });
    }, []);

    return [isLogin, token];
};

export default useAuth;