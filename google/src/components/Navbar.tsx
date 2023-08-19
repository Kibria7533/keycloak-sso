import React, {useEffect} from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import {getLoggedinUser, getSSOLogoutURL} from "../hooks/useKeycloak";
import {KEYCLOAK_LOGIN_URL} from "../utils/keycloak-urls";
import useAuth from "../hooks/useAuth";

const Navbar: React.FC = () => {
    const {accessToken} = useAuth()

    useEffect(() => {
        const fetchUser = async (token: any) => {
            try {
                await getLoggedinUser(token)
                console.log('get user called')
            } catch (e) {
                console.log(e)
            }
        }

        if (accessToken) {
            fetchUser(accessToken)
        }
        // console.log('accesstoken =>', accessToken)
    }, [accessToken]);

    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar sx={{display: 'flex', justifyContent: 'space-between'}}>
                    <Link href="/" underline="none">
                        <Typography variant="h6" component="div" sx={{color: "#fff"}}>
                            Keycloak SSO
                        </Typography>
                    </Link>
                    <Box>
                        {
                            !accessToken && (
                                <Link href={KEYCLOAK_LOGIN_URL} underline="none">
                                    <Button sx={{color: "#fff"}}>Login</Button>
                                </Link>
                            )
                        }
                        {
                            !accessToken && (
                                <Button color="inherit">Signup</Button>
                            )
                        }

                        {accessToken && (<Link href="/profile" underline="none">
                            <Button color="inherit" sx={{color: "#fff"}}>Profile</Button>
                        </Link>)}


                        {accessToken && (
                            <Button color="inherit" sx={{color: "#fff"}}
                                    onClick={() => window.location.href = getSSOLogoutURL()}>
                                Logout</Button>)
                        }
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default Navbar;
