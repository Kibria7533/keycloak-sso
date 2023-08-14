import React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";

interface NavbarProps {
}


const Navbar: React.FC<NavbarProps> = () => {
    const KEYCLOAK_HOST = process.env.REACT_APP_KEYCLOAK_HOST;
    const KEYCLOAK_REALM = process.env.REACT_APP_KEYCLOAK_REALM;
    const KEYCLOAK_CLIENT_ID = process.env.REACT_APP_KEYCLOAK_CLIENT_ID;
    const KEYCLOAK_REDIRECT_URI: string = process.env.REACT_APP_KEYCLOAK_REDIRECT_URI || 'http://localhost:3000/';

    const LOGIN_URL = `${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/auth?` +
        `response_type=code&client_id=${KEYCLOAK_CLIENT_ID}&scope=openid&redirect_uri=${encodeURI(KEYCLOAK_REDIRECT_URI)}`;

    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar sx={{display: 'flex', justifyContent: 'space-between'}}>
                    <Link href="/" underline="none">
                        <Typography variant="h6" component="div">
                            Keycloak SSO
                        </Typography>
                    </Link>
                    <Box>
                        <Link href={LOGIN_URL} underline="none">
                            <Button sx={{color: "#fff"}}>Login</Button>
                        </Link>
                        <Button color="inherit">Signup</Button>
                        <Link href="/profile" underline="none">
                            <Button color="inherit">Profile</Button>
                        </Link>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default Navbar;
