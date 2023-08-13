import Navbar from "./components/Navbar";
import keycloak from "./lib/Keycloak/Keycloak";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import useAuth from "./lib/hooks/useAuth";

function App() {
    const [isLogin, token] = useAuth();
    return (

        <Navbar/>


    );
}

export default App;
