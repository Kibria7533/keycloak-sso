import React, {useContext, useEffect, useState} from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import {getSSOLoginURL, getSSOLogoutURL} from "../lib/Keycloak/KeycloakHelpers";




const Navbar: React.FC = () => {
    const [token, setToken] = useState<any>()
    const LOGIN_URL = getSSOLoginURL()


    useEffect(() => {
        const token = localStorage.getItem('access_token');
        setToken(token)
    }, []);

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
                            !token && (
                                <Link href={LOGIN_URL} underline="none">
                                    <Button sx={{color: "#fff"}}>Login</Button>
                                </Link>
                            )
                        }
                        {
                            !token && (
                                <Button color="inherit">Signup</Button>
                            )
                        }

                        {token && (<Link href="/profile" underline="none">
                            <Button color="inherit" sx={{color: "#fff"}}>Profile</Button>
                        </Link>)}


                        {token && (
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
