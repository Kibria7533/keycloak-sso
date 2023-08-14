import React, {useState, useEffect, useRef} from "react";
import client from "../Keycloak/Keycloak";


const useAuth = () => {
    const isRun = useRef(false);
    const [token, setToken] = useState(null);
    const [isLogin, setLogin] = useState(false);

    useEffect(() => {
        if (isRun.current) return;

        isRun.current = true;
        client
            .init({
                checkLoginIframe: false
            })
            .then((authenticated) => {
                setLogin(authenticated);
            });
    }, []);

    return [isLogin, token];
};

export default useAuth;