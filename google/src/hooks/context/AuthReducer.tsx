import {
    ACTION_LOG_OUT,
    ACTION_SET_ACCESS_TOKEN,
    ACTION_SET_ID_TOKEN,
    ACTION_SET_REFRESH_TOKEN,
    ACTION_SET_USER
} from './AuthActions'

const AuthReducer = (state: any, action: any) => {
    switch (action.type) {
        case ACTION_SET_ACCESS_TOKEN:
            return {...state, accessToken: action.payload}

        case ACTION_SET_REFRESH_TOKEN:
            return {...state, refreshToken: action.payload}

        case ACTION_SET_ID_TOKEN:
            return {...state, idToken: action.payload}

        case ACTION_SET_USER:
            return {...state, user: action.payload}

        case ACTION_LOG_OUT:
            return {...action.payload}

        default:
            return state
    }
}

export default AuthReducer