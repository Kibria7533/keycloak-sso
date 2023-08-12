'use client';

import { Fragment } from "react";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import keycloak from "@/lib/Keycloak/Keycloak";

export default function Home() {
  return (
    <Fragment>

        <Box >
        <Container>
          <Grid
            container
            sx={{
              width: "100%", height: "100vh",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Typography variant="h4">Home Page</Typography>
          </Grid>
        </Container>
        </Box>
    </Fragment>
  );
}
