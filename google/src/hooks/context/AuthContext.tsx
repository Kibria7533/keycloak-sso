import React, {Context, createContext, ReactNode, useReducer} from 'react'
import AuthReducer from './AuthReducer'
import {
    ACTION_LOG_OUT,
    ACTION_SET_ACCESS_TOKEN,
    ACTION_SET_ID_TOKEN,
    ACTION_SET_REFRESH_TOKEN,
    ACTION_SET_USER
} from './AuthActions'
import {
    clearLoginInfo,
    getAccessToken,
    getIdToken,
    getRefreshToken,
    saveAccessToken,
    saveIdToken,
    saveRefreshToken
} from "../../utils/storage";

interface AuthContextType {
    accessToken: string,
    refreshToken: string,
    idToken: string,
    user: object,
    setAccessToken: (token: string) => void,
    setRefreshToken: (token: string) => void,
    setIdToken: (token: string) => void,
    setUser: (user: object) => void,
    signOut: (next: () => {}) => void
}

interface AuthProviderProps {
    children: ReactNode;
}

const initialState: AuthContextType = {
    accessToken: getAccessToken(),
    refreshToken: getRefreshToken(),
    idToken: getIdToken(),
    user: {},
    setAccessToken: () => {
    },
    setRefreshToken: () => {
    },
    setIdToken: () => {
    },
    setUser: () => {},
    signOut: (next: () => {}) => {
    }
}

export const AuthContext: Context<AuthContextType> = createContext(initialState)

export const AuthContextProvider: React.FC<AuthProviderProps> = ({children}) => {
    const [state, dispatch] = useReducer(AuthReducer, initialState)

    const setAccessToken = (token: string): void => {
        saveAccessToken(token)
        dispatch({
            type: ACTION_SET_ACCESS_TOKEN,
            payload: token
        })
    }

    const setRefreshToken = (token: string): void => {
        saveRefreshToken(token)
        dispatch({
            type: ACTION_SET_REFRESH_TOKEN,
            payload: token
        })
    }

    const setIdToken = (token: string): void => {
        saveIdToken(token)
        dispatch({
            type: ACTION_SET_ID_TOKEN,
            payload: token
        })
    }

    const setUser = (user: object): void => {
        dispatch({
            type: ACTION_SET_USER,
            payload: user,
        })
    }

    const signOut = (next: () => {}): void => {
        clearLoginInfo()
        dispatch({
            type: ACTION_LOG_OUT
        })
        next()
    }

    const authContextValue: AuthContextType = {
        accessToken: state.accessToken,
        setAccessToken,
        refreshToken: state.refreshToken,
        setRefreshToken,
        idToken: state.idToken,
        setIdToken,
        user: state.user,
        setUser,
        signOut
    }

    return (
        <AuthContext.Provider value={authContextValue}>
            {children}
        </AuthContext.Provider>
    );
}