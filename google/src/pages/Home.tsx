import React, {useEffect, useRef} from 'react';
import useAuth from "../hooks/useAuth";
import {useLocation, useNavigate} from "react-router-dom";
import {getKeycloakToken} from "../hooks/useKeycloak";

const Home: React.FC = () => {
    const {setAccessToken, setRefreshToken, setIdToken} = useAuth()
    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const authorizationCode = queryParams.get('code')
    const redirectUrl = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || ''

    const isMounted = useRef()

    useEffect(() => {
        if (isMounted.current) return

        const fetchData = async () => {
            try {
                const values = {
                    redirect_uri: encodeURI(redirectUrl.toString()),
                    code: authorizationCode,
                }

                const {data} = await getKeycloakToken(values)
                setAccessToken(data?.access_token)
                setRefreshToken(data?.refresh_token)
                setIdToken(data?.id_token)
                navigate('/', {replace: true});
            } catch (e) {
                console.log(e)
            }
        }

        authorizationCode && fetchData()

        // @ts-ignore
        isMounted.current = true
    }, []);

    return <h2>Home -</h2>;
};

export default Home;
