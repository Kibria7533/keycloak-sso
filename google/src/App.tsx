import React from 'react';
import {Route, Routes,} from 'react-router-dom';
import AppRoutes from "./Routes";


function App() {
    return (
        <Routes>
            <Route path="/*" element={<AppRoutes/>}/>
        </Routes>
    );
}

export default App;
