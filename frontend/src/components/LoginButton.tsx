import React from 'react';

const LoginButton = () => {
    const handleAuthClick = () => {
        // Construct the Keycloak authorization URL
        const keycloakUrl = 'http://your-keycloak-server/auth/realms/your-realm';
        const clientId = 'your-client-id';
        const redirectUri = encodeURIComponent('http://your-frontend-app/redirect-uri');
        const authUrl = `${keycloakUrl}/protocol/openid-connect/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;

        // Redirect to Keycloak for authentication
        window.location.href = authUrl;
    };

    return (
        <button onClick={handleAuthClick}>Login with Keycloak</button>
    );
};

export default LoginButton;
