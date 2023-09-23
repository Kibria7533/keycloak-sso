import { Routes, Route } from "react-router-dom"
import Layout from "./layout/Layout";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import React from "react";
import SignUp from "./pages/Signup";

const AppRoutes = () => {
    return (
        <Routes>
            <Route>
                <Route element={<Layout />}>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/profile" element={<Profile/>}/>
                    <Route path="/signup" element={<SignUp/>}/>
                </Route>
            </Route>
        </Routes>
    )
}

export default AppRoutes