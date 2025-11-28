import { useEffect, useState } from "react";
import { api_path } from "../../../../util/apipath";
import { setCantidad } from "../../../../util/carrito";
import { FetchedImage } from "../../FetchedImage/FetchedImage";
import { NumberSpinner } from "../../Input/NumberSpinner";

import './CartItemCard.css'

export function CartItemCard({ cartItem }) {
    let [cant, setCant] = useState(cartItem.cantidad)

    useEffect(() => {
        setCantidad(cartItem.producto.id, cant);
    }, [cant])

    return <div className="cartItemCard">
        <FetchedImage src={api_path(cartItem.producto.imagenUrl)} />
        <div className="detalles">
            <h1>{cartItem.producto.nombre}</h1>
            <h3>{cartItem.producto.detalle}</h3>
        </div>
        <div className="meta">
            <h1>${cartItem.producto.valor + cartItem.producto.iva}</h1>
            <NumberSpinner value={cant} setValue={setCant} min={0} max={10} />
        </div>
    </div>
}
