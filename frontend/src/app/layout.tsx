'use client'

import Navbar from '@/components/Navbar'
import './globals.css'
import {Inter} from 'next/font/google'
import keycloak2 from "@/lib/Keycloak/Keycloak2";
import {ReactKeycloakProvider} from "@react-keycloak/web";

const inter = Inter({subsets: ['latin']})


export default function RootLayout({children}: { children: React.ReactNode }) {
    return (
        <html lang="en">
            <body className={inter.className}>
                <ReactKeycloakProvider
                    authClient={keycloak2}> <Navbar/>
                </ReactKeycloakProvider>
                {children}
            </body>
        </html>
    )
}
