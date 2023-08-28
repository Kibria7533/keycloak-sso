import {useEffect, useRef, useState} from "react";
import Keycloak from "keycloak-js";

const keycloakConfig: Keycloak.KeycloakConfig = {
    url: 'http://localhost:8180/',
    realm: 'google',
    clientId: 'google-cli',
};

const client = new Keycloak(keycloakConfig);

const keycloakInit = (): [boolean] => {
    const isRun = useRef(false);
    const [isLogin, setLogin] = useState<boolean>(false);

    useEffect(() => {
        if (isRun.current) return;

        isRun.current = true;
        client
            .init({
                checkLoginIframe: false,
            })
            .then((authenticated) => {
                setLogin(authenticated);
            });
    }, []);

    return [isLogin];
};

export default keycloakInit;
