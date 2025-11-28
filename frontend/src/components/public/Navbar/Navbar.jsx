import { Link, useLocation } from "react-router-dom";
import "./Navbar.css"
import { CartIcon } from "../cart/CartIcon/CartIcon";
import { isLoggedIn } from "../../../util/session";
import { useEffect, useState } from "react";

export function Navbar() {
    const [loggedIn, setLoggedIn] = useState(isLoggedIn());
    const location = useLocation();

    useEffect(() => {
        setLoggedIn(isLoggedIn());
    }, [location])

    return <>
        <nav className="navbar">
            <div>
                <Link className="sosdrink-logo" to="/" aria-label="Inicio">
                    <p>SOSDRINK</p>
                </Link>
                <hr />
                <ul className="navbar-links">
                    <li><Link to="/productos" aria-label="Productos">Productos</Link></li>
                    <li><Link to="/blogs" aria-label="Blogs">Blogs</Link></li>
                    <li><Link to="/nosotros" aria-label="Nosotros">Nosotros</Link></li>
                    <li><Link to="/contacto" aria-label="Contacto">Contacto</Link></li>
                    {loggedIn ? <li><Link to="/compras" aria-label="Compras">Compras</Link></li> : <li><Link to="/login" aria-label="Login">Ingresa</Link></li>}
                </ul>
                <div className="navbar-buttons">
                    {loggedIn ? <CartIcon /> : <></>}
                </div>
                <hr />
            </div>
        </nav>
    </>
}
