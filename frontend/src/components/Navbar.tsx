'use client';
import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Link from "next/link";
import {useKeycloak} from "@react-keycloak/web";


export default function Navbar() {
    const { keycloak, initialized } = useKeycloak()
// console.log(keycloak.createLoginUrl())
console.log(keycloak)
    const handleAuthClick = () => {
    keycloak.login()
    };


    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar sx={{display: 'flex', justifyContent: 'space-between'}}>
                    <Link href="/" passHref>
                        <Typography variant="h6" component="div">
                            Keycloak SSO
                        </Typography>
                    </Link>
                    <Box>
                        <Button color="inherit" onClick={handleAuthClick}>Login</Button>
                        <Button color="inherit">Signup</Button>
                        <Link href="/profile" passHref>
                            <Button color="inherit">Profile</Button>
                        </Link>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
