import { useEffect, useState } from "react"
import { useParams } from "react-router-dom";
import { fetchWithToken } from "../../../util/session";
import { api_path } from "../../../util/apipath";

import "./BoletaPage.css"

export function BoletaPage() {
    const [boleta, setBoleta] = useState();
    const { folio } = useParams();

    useEffect(() => {
        const fetchBoleta = async () => {
            const response = await fetchWithToken(api_path(`api/compras/${folio}`));
            if (response.ok) {
                const data = await response.json();
                setBoleta(data);
                console.log(data);
            }
        }
        fetchBoleta();
    }, [folio]);

    const Container = ({ children }) => {
        return <div className="boleta-container">{children}</div>
    }

    if (boleta) {
        return <Container>
            <div className="boleta">
                <div className="meta">
                    <h5 id="boleta-fecha">{boleta.fechaEmision}</h5>
                    <h3 id="boleta-folio">Folio N.{boleta.folio}</h3>
                </div>
                <div className="split">
                    <div className="empresa">
                        <h1>Empresa</h1>
                        <p>{boleta.empresaRazonSocial} - RUT {boleta.empresaRUT}</p>
                        <p>{boleta.empresaDireccion}</p>
                        <p>{boleta.empresaCorreo} - {boleta.empresaTelefono}</p>
                        <p className="giro">{boleta.empresaGiro}</p>
                    </div>
                    <div className="cliente">
                        <h1>Cliente</h1>
                        <p>{boleta.clienteNombre}</p>
                        <p>RUN {boleta.clienteRUN}</p>
                    </div>
                </div>
                <table className="tabla">
                    <tr>
                        <th>Producto</th>
                        <th>Valor</th>
                        <th>IVA</th>
                    </tr>
                    {
                        boleta.lineas.map((i) => <tr>
                            <td>{i.detalle}</td>
                            <td>${i.valorUnitario}</td>
                            <td>${i.ivaUnitario}</td>
                        </tr>)
                    }
                </table>
                <div className="sumatoria">
                    <h4>Subtotal ${boleta.subtotal}</h4>
                    <h4>Total ${boleta.total}</h4>
                </div>
                <div className="iva-cont">
                    <p>IVA ${boleta.iva}</p>
                </div>
            </div>
        </Container>
    }

    return <Container></Container>
}
