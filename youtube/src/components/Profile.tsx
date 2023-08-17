import React, {Fragment, useEffect} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {getSSOTokenURL} from "../lib/Keycloak/KeycloakHelpers";

let FLAG=false

const fetchData = async (redirectUrl:any, authorizationCode:any) => {
    const data = await getSSOTokenURL(redirectUrl, authorizationCode)
    if (data && data.access_token && data.refresh_token && data.id_token) {
        localStorage.setItem('access_token', data.access_token);
        localStorage.setItem('refresh_token', data.refresh_token);
        localStorage.setItem('id_token', data.id_token);
    }
    FLAG=true
}

const Profile: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const token=localStorage.getItem('access_token');
    const queryParams = new URLSearchParams(location.search);
    const authorizationCode = queryParams.get('code')
    const redirectUrl = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || ''

    useEffect( () => {
        fetchData(redirectUrl, authorizationCode)
        if(FLAG && !token) navigate('/')
    }, []);

    return (
        <Fragment>
            <div>This is the profile page.</div>
        </Fragment>

    );
};

export default Profile;
