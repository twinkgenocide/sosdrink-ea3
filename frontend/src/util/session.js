import { api_path } from "./apipath";

export async function login(email, password) {
    if (localStorage.getItem('authToken') != null) return;

    const response = await fetch(api_path("auth/login"), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ correo: email, clave: password })
    });

    const data = await response.json();
    localStorage.setItem('authToken', data.token);
}

export async function logoff() {
    localStorage.removeItem('authToken');
}

export function isLoggedIn() {
    return localStorage.getItem('authToken') != null;
}

export async function fetchWithToken(url, options = {}) {
    const currentToken = localStorage.getItem('authToken');
    const headers = {
        ...options.headers,
        ...(currentToken ? { 'Authorization': `Bearer ${currentToken}` } : {})
    }

    const opts = {
        ...options,
        headers: headers
    };

    const response = await fetch(url, opts)
    return response;
}

// login("felipe.rojas@duoc.cl", "Cliente456#");
