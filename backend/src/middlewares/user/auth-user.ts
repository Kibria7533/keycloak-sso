import {AsyncLocalStorage} from "async_hooks";

interface AuthUser {
    id: string,
    createdTimestamp: number,
    username: string,
    enabled: boolean,
    totp: boolean,
    emailVerified: boolean,
    firstName: string,
    lastName: string,
    disableableCredentialTypes: Array<any>,
    requiredActions: Array<any>,
    notBefore: number,
    access: any
}

export const AuthUser = {
    storage: new AsyncLocalStorage<AuthUser>(),
    get(): any {
        return this.storage.getStore();
    },
    set(user: AuthUser): any {
        return this.storage.enterWith(user);
    },
};