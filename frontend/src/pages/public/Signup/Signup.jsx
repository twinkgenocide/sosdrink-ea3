import { useNavigate } from "react-router-dom"
import "./Signup.css"
import { useEffect, useState } from "react";
import { api_path } from "../../../util/apipath";
import { setToken } from "../../../util/session";

export function Signup() {
    const [error, setError] = useState(null);
    const [shake, setShake] = useState(false);
    const [disabled, setDisabled] = useState(false);

    const navigate = useNavigate();

    const onSubmit = async (e) => {
        e.preventDefault();

        if (disabled) return;

        setError(null);
        setDisabled(true);

        const form = document.getElementById("signup");
        if (!form.reportValidity()) {
            setDisabled(false);
            return;
        }

        const pass1 = document.getElementById("clave").value;
        const pass2 = document.getElementById("clave_confirmar").value;
        if (pass1 != pass2) {
            setError("¡Las claves deben ser iguales!");
            return;
        }

        const userData = Object.fromEntries(new FormData(form).entries());
        delete userData.clave_confirmar;
        console.log(JSON.stringify(userData));
        const sendRequest = async () => {
            return await fetch(api_path("auth/signup"), {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            })
        }

        const response = await sendRequest();

        if (!response.ok) {
            setError("¡Error! Verifique sus datos e intente otra vez.");
            return;
        }

        const data = await response.json();
        if (!data.token) {
            setError("Error interno del servidor. Por favor intente otra vez.");
            return;
        }

        setToken(data.token);
        navigate("/");
    }

    useEffect(() => {
        if (error != null) {
            setShake(true);
            setTimeout(() => { setShake(false); setDisabled(false) }, 500);
        }
    }, [error])

    return <div className="signup-container">
        <h1>Crear cuenta</h1>
        <form className="signupForm" id="signup">
            <div className="formInternal">
                <div className="personalInformation">
                    <label for="run">RUN</label>
                    <input type="text" id="run" name="run" required />

                    <label for="nombre">Nombre</label>
                    <input type="text" id="nombre" name="nombre" required />

                    <label for="apellidos">Apellidos</label>
                    <input type="text" id="apellidos" name="apellidos" required />

                    <label for="direccion">Direccion (Opcional)</label>
                    <input type="text" id="direccion" name="direccion" />
                </div>
                <div className="authentication">
                    <label for="correo">Correo</label>
                    <input type="email" id="correo" name="correo" required />

                    <label for="clave">Contraseña</label>
                    <input type="password" id="clave" name="clave" required />

                    <label for="clave_confirmar">Confirme su contraseña</label>
                    <input type="password" id="clave_confirmar" name="clave_confirmar" required />

                </div>
            </div>
            <button className={`${disabled ? 'disabled' : ''}`} type="submit" onClick={onSubmit}>REGISTRAR</button>
        </form>
        <p className={`signup-error ${shake ? 'shake' : ''}`}>{error}</p>
    </div>
}
