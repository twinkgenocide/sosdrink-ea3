import "./Signup.css"

export function Signup() {
    return <form className="signupForm">
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
                <input type="text" id="correo" name="correo" required />

                <label for="clave">Contraseña</label>
                <input type="text" id="clave" name="clave" required />

                <label for="clave">Confirme su contraseña</label>
                <input type="text" id="clave_confirmar" name="clave_confirmar" required />

            </div>
        </div>
        <button type="submit">REGISTRAR</button>
    </form>
}
