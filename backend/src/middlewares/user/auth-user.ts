import {AsyncLocalStorage} from "async_hooks";

interface AuthUser {
    id: string,
    username: string,
    firstName: string,
    lastName: string,
    role?: any


}

export const AuthUser = {
    storage: new AsyncLocalStorage<AuthUser>(),
    get(): any {
        return this.storage.getStore();
    },
    set(user: AuthUser): any {
        return this.storage.enterWith(user);
    },
    addRole(role: any): void {
        const user = this.get();
        if (user && Array.isArray(role)) user.roles = role
        else user.roles.push(role);
        this.set(user);
    },
};