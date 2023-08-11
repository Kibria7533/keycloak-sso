"use client";
import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import TextFieldCommon from "@/components/common/TextFieldCommon";


const boxSx={
    marginTop: 8,
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  }

export default function Login() {
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <Container component="main" maxWidth="xs">
     
      <Box
        sx={boxSx}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
         Sign Up
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextFieldCommon
            label="Full Name"
            name="name"
            autoFocus
            required
            fullWidth
          />
  
          <TextFieldCommon
            label="Email Address"
            name="email"
            autoFocus
            required
            fullWidth
          />
  


          <TextFieldCommon
            label="Password"
            name="password"
            type="password"
            required
            fullWidth
          />
          <TextFieldCommon
            label="Confirm Password"
            name="confirm-password"
            type="confirm-password"
            required
            fullWidth
          />

          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Create 
          </Button>
          
        </Box>
      </Box>
    </Container>
  );
}
