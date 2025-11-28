import { useNavigate } from "react-router-dom";
import { api_path } from "./apipath";
import { isLoggedIn, fetchWithToken } from "./session";

let updateTimer;
let carritoLocal = JSON.parse(localStorage.getItem('carrito')) || [];

updateCarrito();

export function addToCarrito(productId) {
    if (!isLoggedIn()) {
        window.location.href = "/login" // quebrando las leyes de react
        return;
    }

    const existing = carritoLocal.find(item => item.producto.id == productId);

    if (!existing) {
        getCarrito().push({ id: -1, cantidad: 1, producto: { id: productId } });
        saveCarrito()
    }

    const url = api_path(`api/carrito?productoId=${productId}`);
    fetchWithToken(url, { method: 'PUT' })
    updateCarrito();
}

export function removeFromCarrito(productId) {
    const index = carritoLocal.findIndex((item) => item.producto.id == productId);
    if (index != -1) {
        const cartItemId = carritoLocal[index].id;
        carritoLocal.splice(index, 1);
        saveCarrito()

        const url = api_path(`api/carrito/${cartItemId}`);
        fetchWithToken(url, { method: 'DELETE' })
        updateCarrito();
    }
}

export function findInCarrito(productId) {
    return carritoLocal.find((item) => item.producto.id == productId);
}

export function setCantidad(productId, cantidad) {
    const p = findInCarrito(productId);
    if (p != null) {
        const url = api_path(`api/carrito/${p.id}?cantidad=${cantidad}`);
        fetchWithToken(url, { method: 'PUT' })
        updateCarrito();
    }
}

export function updateCarrito() {
    clearTimeout(updateTimer);

    updateTimer = setTimeout(async () => {
        const url = api_path("api/carrito");
        const response = await fetchWithToken(url);
        if (response.ok) {
            const data = await response.json();
            carritoLocal = data;
            saveCarrito();
        }
    }, 400);
}

export function isInCarrito(productId) {
    return carritoLocal.find((i) => i.producto.id == productId) != null;
}

export function getCarrito() {
    return carritoLocal;
}

export function saveCarrito() {
    localStorage.setItem('carrito', JSON.stringify(getCarrito()))
    console.log(carritoLocal)
}
