import React, {useState, useEffect, useRef} from "react";

import client from "../Keycloak/Keycloak";

const useAuth = (): [boolean] => {
    const isRun = useRef(false);
    const [token, setToken] = useState<string | null>(null);
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

export default useAuth;
