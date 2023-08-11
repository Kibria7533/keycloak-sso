import Image from "next/image";
import styles from "./page.module.css";
import { Fragment } from "react";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";

const defaultTheme = createTheme();

export default function Home() {
  return (
    <Fragment>
      <ThemeProvider theme={defaultTheme}>
        <CssBaseline />
        <Container>
          <Grid
            container
            sx={{
              width: "100%",
              height: "100vh",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Typography variant="h4">Home page</Typography>
          </Grid>
        </Container>
      </ThemeProvider>
    </Fragment>
  );
}
