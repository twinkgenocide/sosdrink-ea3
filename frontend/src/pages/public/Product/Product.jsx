import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { api_path } from "../../../util/apipath";
import { Spinner } from "../../../components/public/Spinner/Spinner";

import "./Product.css"
import { NeonButton } from "../../../components/public/Input/Buttons";
import { NumberSpinner } from "../../../components/public/Input/NumberSpinner";
import { ProductCatalog } from "../ProductCatalog/ProductCatalog";
import { addToCarrito, findInCarrito, isInCarrito, removeFromCarrito, setCantidad } from "../../../util/carrito";

export function Product() {
    const params = useParams();
    let [product, setProduct] = useState();

    useEffect(() => {
        setProduct();
        const url = api_path(`api/productos/${params.productoId}`)
        const fetchData = async () => {
            const response = await fetch(url);
            if (response.ok) {
                const data = await response.json();
                setProduct(data)
            }
        }
        fetchData();
    }, [params]);

    return <div className="product-container">
        {
            product ?
                <>
                    <ProductBody product={product} />
                    <hr />
                    <ProductCatalog tipo={product.tipoProducto.id} skip={product.id} scroll={true} texto='Productos relacionados' />
                </>
                : <Spinner />
        }
    </div>
}

function ProductBody({ product }) {
    let [inCarrito, setInCarrito] = useState(false);
    let [value, setValue] = useState(0);

    useEffect(() => {
        const item = findInCarrito(product.id);
        if (item) {
            setInCarrito(true);
            setValue(item.cantidad)
        } else {
            setInCarrito(false);
        }
    }, [])

    const setCant = (cantidad) => {
        setValue(cantidad);
        setCantidad(product.id, cantidad);
    }

    const addToCart = () => {
        addToCarrito(product.id);
        setInCarrito(isInCarrito(product.id));
        setValue(1);
    }

    const removeFromCart = () => {
        removeFromCarrito(product.id);
        setInCarrito(isInCarrito(product.id));
        setValue(0);
    }

    return <article className="product">
        <img src={api_path(product.imagen)} />
        <div className="product-card">
            <div className="product-info">
                <h2 className="product-name">{product.nombre}
                    <span className="product-price">{`$${product.valor + product.iva}`}</span>
                </h2>
                <p className="product-details">{product.detalle}</p>
            </div>
            <div className="product-actions">
                <NumberSpinner value={value} setValue={setCant} disabled={!inCarrito} />
                {
                    !inCarrito ? <NeonButton onClick={addToCart}>Añadir al carrito</NeonButton>
                        : <NeonButton negative={true} onClick={removeFromCart}>Eliminar del carrito</NeonButton>
                }
            </div>
        </div>
    </article>
}
