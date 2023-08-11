'use client'

import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";



export default function Profile(){
return (

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
      <Typography variant="h4">Profile Page</Typography>
    </Grid>
  </Container>

)

}
