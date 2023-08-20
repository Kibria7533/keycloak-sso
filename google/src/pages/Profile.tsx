import React, {Fragment, useEffect, useRef} from 'react';
import {getProfile} from "../hooks/useKeycloak";
import useAuth from "../hooks/useAuth";


const Profile: React.FC = () => {
    const {accessToken, setUser, user} = useAuth()

    const isMounted = useRef()

    useEffect(() => {
        if (isMounted.current) return

        const fetchData = async () => {
            try {
                const {data} = await getProfile(accessToken)
                setUser(data?.data)
            } catch (e) {
                console.log(e)
            }
        }

        accessToken && fetchData()

        // @ts-ignore
        isMounted.current = true
    }, []);

    return (
        <Fragment>
            <h2>Profile - </h2>
            <div>
                <pre>{JSON.stringify(user, null, 2)}</pre>
            </div>
        </Fragment>

    );
};

export default Profile;
