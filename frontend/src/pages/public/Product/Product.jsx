import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { api_path } from "../../../util/apipath";
import { Spinner } from "../../../components/public/Spinner/Spinner";

import "./Product.css"
import { NeonButton } from "../../../components/public/Input/Buttons";
import { NumberSpinner } from "../../../components/public/Input/NumberSpinner";
import { ProductCatalog } from "../ProductCatalog/ProductCatalog";

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

    return <article className="product">
        <img src={api_path(product.imagen)} />
        <div className="product-card">
            <div className="product-info">
                <h2 className="product-name">{product.nombre}
                    <span className="product-price">{`$${product.precio}`}</span>
                </h2>
                <p className="product-details">{product.detalle}</p>
            </div>
            <div className="product-actions">
                <NumberSpinner value={value} setValue={setValue} />
                {
                    !inCarrito ? <NeonButton onClick={() => setInCarrito(true)}>Añadir al carrito</NeonButton>
                        : <NeonButton negative={true} onClick={() => setInCarrito(false)}>Eliminar del carrito</NeonButton>
                }
            </div>
        </div>
    </article>
}
