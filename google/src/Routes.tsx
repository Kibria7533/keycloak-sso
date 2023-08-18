import { Routes, Route } from "react-router-dom"
import Layout from "./layout/Layout";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import React from "react";

const AppRoutes = () => {
    return (
        <Routes>
            <Route>
                <Route element={<Layout />}>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/profile" element={<Profile/>}/>
                </Route>
            </Route>
        </Routes>
    )
}

export default AppRoutes