import { useEffect, useState } from "react";
import { api_path } from "../../../../util/apipath";
import { useNavigate } from "react-router-dom";
import { Spinner } from "../../Spinner/Spinner";
import { Tag } from "../../Tag/Tag";

import "./ProductMiniature.css";
import { CartButtonDummy } from "../../cart/CartButtons/CartButtons";

export function ProductMiniature({ product }) {
    const [image, setImage] = useState();
    const url = api_path(product.imagen);
    const navigate = useNavigate();

    const fetchImage = async () => {
        const res = await fetch(url);
        const blob = await res.blob();
        setTimeout(() => {
            setImage(URL.createObjectURL(blob));
        }, Math.floor(Math.random() * 2000))
    }

    useEffect(() => {
        fetchImage();
    }, []);

    let classes = ["product-miniature"];

    if (product.stock == 0) {
        classes.push("no-stock")
    } else if (product.stock <= product.criticalStock) {
        classes.push("critical-stock");
    }

    return <div className={classes.join(' ')} onClick={(e) => {
        if (product.stock == 0 || e.target.closest("a") || e.target.closest("button")) return;
        navigate(`/productos/${product.id}`)
    }}>
        {
            image ? <ProductMiniContent product={product} image={image} showButton={product.stock > 0} /> : <Spinner />
        }
    </div>
}

function ProductMiniContent({ product, image, showButton }) {
    let button = null;

    if (showButton) {
        button = <CartButtonDummy productId={product.id} />
    }

    console.log(product);

    return <div className="content">
        <img src={image} />
        <h1 className="name">{product.nombre}</h1>
        <p className="price">{`$${product.valor + product.iva}`}</p>
        <Tag href={`/productos?tipo=${product.tipoProducto.id}`}>{product.tipoProducto.nombre}</Tag>
        {button}
    </div>
}
