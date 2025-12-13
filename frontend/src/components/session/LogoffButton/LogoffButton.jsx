import { useNavigate } from "react-router-dom";
import { logoff } from "../../../util/session";
import './LogoffButton.css'

export function LogoffButton() {
    const navigate = useNavigate();
    const doLogoff = () => {
        logoff();
        navigate("/");
    }
    return <button onClick={doLogoff} id="logoff-button">Cerrar Sesión</button>
}
