import { useNavigate } from "react-router-dom";
import { CartItemCard } from "../../../components/public/cart/CartItemCard/CartItemCard";
import { NeonButton } from "../../../components/public/Input/Buttons";
import { api_path } from "../../../util/apipath";
import { getCarrito } from "../../../util/carrito";
import { fetchWithToken, isLoggedIn } from "../../../util/session";

import './CarritoPage.css'
import { useEffect, useState } from "react";

export function CarritoPage() {
    const navigate = useNavigate();
    const [items, setItems] = useState([]);

    if (!isLoggedIn()) {
        navigate("/login")
        return <></>
    }

    useEffect(() => {
        setItems(getCarrito());
    }, [])

    const pagar = async () => {
        const result = await fetchWithToken(api_path("api/compras"), { method: 'POST' });
        if (result.ok) {
            const data = await result.json();
            const folio = data.folio;
            navigate(`/compras/${folio}`);
        }
    }

    const Container = ({ children }) => {
        return <div className="carrito-container">
            <div>{children}</div>
        </div>
    }

    if (items.length > 0) {
        return <Container>
            {items.map((item) => {
                return <CartItemCard key={item.id} cartItem={item} />
            })}
            <NeonButton onClick={() => pagar()}>IR A PAGAR</NeonButton>
        </Container>
    }

    return <Container>
        <h2>Nada por aquí 🥴</h2>
        <p>Intenta agregar productos a tu carrito!</p>
    </Container>
}
