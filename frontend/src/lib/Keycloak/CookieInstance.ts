import { Cookies } from 'react-cookie';
import { Cookie, CookieGetOptions, CookieSetOptions } from 'universal-cookie/cjs/types';


// Create a new instance of Cookies
const cookieInstance = new Cookies();

// Get a browser cookie by name
export const getBrowserCookie = (name: string, options?: CookieGetOptions) => {
    return cookieInstance.get(name, options);
};

// Set a browser cookie
export const setBrowserCookie = (
    name: string,
    value: Cookie,
    options?: CookieSetOptions,
) => {
    // Default options for the cookie
    const defaultOptions: CookieSetOptions = {
        path: '/',
        domain: 'localhost',
    };

    // Merge the provided options with default options
    const mergedOptions = typeof options !== 'undefined'
        ? { ...defaultOptions, ...options }
        : defaultOptions;

    return cookieInstance.set(name, value, mergedOptions);
};

// Remove a browser cookie by name
export const removeBrowserCookie = (
    name: string,
    options?: CookieSetOptions,
) => {
    // Default options for cookie removal
    const defaultOptions: CookieSetOptions = {
        path: '/',
        domain: 'localhost',
    };

    // Merge the provided options with default options
    const mergedOptions = typeof options !== 'undefined'
        ? { ...defaultOptions, ...options }
        : defaultOptions;

    return cookieInstance.remove(name, mergedOptions);
};

// Export the cookie instance
export default cookieInstance;
