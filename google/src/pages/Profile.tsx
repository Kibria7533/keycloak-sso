import React, {Fragment, useEffect, useRef} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {getSSOTokenURL} from "../hooks/useKeycloak";
import {getAccessToken, setAccessToken, setIdToken, setRefreshToken} from "../utils/storage";

const Profile: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const queryParams = new URLSearchParams(location.search);

    const token= getAccessToken();
    const authorizationCode = queryParams.get('code')
    const redirectUrl = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || ''

    const isMounted = useRef()

    useEffect( () => {
        if (isMounted.current) return

        const fetchData = async () => {
            try {
                // const data = await getKeycloakToken(authorizationCode)
                const data = await getSSOTokenURL(redirectUrl, authorizationCode)
                console.log("response  => ", data)
                setAccessToken(data?.access_token)
                setRefreshToken(data?.refresh_token)
                setIdToken(data?.id_token)
                // navigate(0)
                navigate('/profile') // todo Redirect without previous history
            } catch (e) {
                console.log(e)
            }
        }

        fetchData()

        // @ts-ignore
        isMounted.current = true

        // if(FLAG && !token) navigate('/')
    }, []);

    return (
        <Fragment>
            <div>This is the profile page.</div>
        </Fragment>

    );
};

export default Profile;
