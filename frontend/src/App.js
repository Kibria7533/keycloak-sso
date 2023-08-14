import Navbar from "./components/Navbar";
import useAuth from "./lib/hooks/useAuth";
import {Fragment} from "react";

function App() {
    const [isLogin, token] = useAuth();
    return (
        <Fragment>
            <Navbar/>
        </Fragment>
    );
}

export default App;
