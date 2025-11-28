import PlusIcon from "./svg/plus.svg?react"
import MinusIcon from "./svg/minus.svg?react"
import "./CartButtons.css"
import { useEffect, useState } from "react"
import { addToCarrito, removeFromCarrito, isInCarrito } from "../../../../util/carrito"

export function CartButtonPlus({ productId, onClick }) {
    return <button className='cart-btn plus' onClick={onClick} aria-label='Agregar al carrito'><PlusIcon /></button>
}

export function CartButtonMinus({ productId, onClick }) {
    return <button className='cart-btn minus' onClick={onClick} aria-label='Quitar del carrito'><MinusIcon /></button>
}

export function CartButtonCross({ productId, onClick }) {
    return <button className='cart-btn cross' onClick={onClick} aria-label='Eliminar del carrito'><PlusIcon /></button>
}

export function CartButtonDummy({ productId }) {
    let [positive, setPositive] = useState(!isInCarrito(productId));

    const onClick = () => {
        if (isInCarrito(productId)) {
            removeFromCarrito(productId);
            setPositive(true);
        } else {
            addToCarrito(productId);
            setPositive(false);
        }
    }

    if (positive) {
        return <CartButtonPlus onClick={onClick} />
    } else {
        return <CartButtonCross onClick={onClick} />
    }
}
