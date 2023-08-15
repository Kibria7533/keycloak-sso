import React, {Fragment} from 'react';
import Navbar from "./components/Navbar";
import {Route, Routes,} from 'react-router-dom';
import Profile from "./components/Profile";
import Home from "./components/Home";


function App() {

    return (
        <Fragment>
            <Navbar/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/profile" element={<Profile/>}/>
            </Routes>
        </Fragment>
    );
}

export default App;
