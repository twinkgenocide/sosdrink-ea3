import { useEffect, useState } from "react";
import { api_path } from "../../../util/apipath";
import { fetchWithToken } from "../../../util/session";

import "./HistorialCompra.css"
import { Link, useNavigate } from "react-router-dom";

export function HistorialCompra() {
    const [boletas, setBoletas] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetchWithToken(api_path("api/compras"));
            if (response.ok) {
                const data = await response.json();
                setBoletas(data);
            }
        }
        fetchData();
    }, [])

    if (boletas.length <= 0) {
        return <></>
    }

    return <div className="historialCompra">
        {boletas.map((boleta) => <CompraHistorica boleta={boleta} />)}
    </div>
}

function CompraHistorica({ boleta }) {
    const navigate = useNavigate();
    console.log(boleta);
    return <Link className="compraHistorica" to={`/compras/${boleta.folio}`}>
        <div className="productNames">
            {boleta.lineas.slice(0, 3).map((item) => <p>{item.detalle + " x" + item.cantidad}</p>)}
        </div>
        <div className="hcont">
            <div className="vcont">
                <p>N. {boleta.folio}</p>
                <p>{boleta.fechaEmision}</p>
            </div>
            <div className="vcont">
                <p>Precio final</p>
                <p>${boleta.total} c/ IVA</p>
            </div>
        </div>
    </Link>
}
