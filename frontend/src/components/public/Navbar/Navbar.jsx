import { Link } from "react-router-dom";
import "./Navbar.css"

export function Navbar() {
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
                    <li><Link to="/login" aria-label="Login">Ingresa</Link></li>
                </ul>
                <hr />
            </div>
        </nav>
    </>
}
