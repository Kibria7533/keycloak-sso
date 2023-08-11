'use client';
import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Link from "next/link";


export default function Navbar() {
    const handleAuthClick = () => {

        const keycloakUrl = 'http://localhost:8080/auth/realms/dev';
        const clientId = 'dev-client';
        const redirectUri = encodeURIComponent('http://localhost:3000/profile');
        const authUrl = `${keycloakUrl}/protocol/openid-connect/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;

        window.location.href = authUrl;

    };
  return (
    <Box sx={{ flexGrow: 1 }}>
    <AppBar position="static">
      <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
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
