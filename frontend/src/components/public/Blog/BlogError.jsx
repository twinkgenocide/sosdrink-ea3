import { Link } from "react-router-dom";

import "./BlogError.css"

export function BlogError({ code }) {
    let message = "Un error ha ocurrido :(";
    if (code == 404) message = "No encontramos este blog :("

    return <div className="blog-error">
        <h1>Algo salió mal 😵</h1>
        <h3>{message}</h3>
        <Link to="/blogs">¡Revisa otros blogs!</Link>
    </div>
}
