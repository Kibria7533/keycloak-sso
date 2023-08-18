import React, {Fragment} from "react";
import {Outlet} from "react-router-dom";
import {Container} from "@mui/material";
import Navbar from "../components/Navbar";

const Layout = () => {
    return (
        <Fragment>
            <Navbar/>
            <Container maxWidth='lg'>
                <Outlet />
            </Container>
        </Fragment>
    )
}

export default Layout