import { createBrowserRouter } from "react-router-dom"
import { LayoutPublic } from "../layouts/LayoutPublic"
import { BlogPage } from "../pages/public/Blog/BlogPage.jsx"
import { BoletaPage } from "../pages/public/Boleta/BoletaPage.jsx"
import { BlogCatalogPage } from "../pages/public/BlogCatalog/BlogCatalog"
import { ProductCatalog } from "../pages/public/ProductCatalog/ProductCatalog.jsx"
import { Product } from "../pages/public/Product/Product.jsx"
import { LoginPage } from "../pages/public/Login/Login"
import { LayoutAdmin } from "../layouts/LayoutAdmin"
import { Saludo } from "../components/admin/Saludo.jsx"
import { AdminUsers } from "../pages/admin/Usuarios"
import { AdminProductos } from "../pages/admin/Productos"
import { CarritoPage } from "../pages/public/Carrito/CarritoPage.jsx"

export const router = createBrowserRouter([
  {
    path: '/',
    element: <LayoutPublic />,
    children: [
      {
        path: 'blogs',
        element: <BlogCatalogPage />
      },
      {
        path: 'blogs/:blogId',
        element: <BlogPage />
      },
      {
        path: 'productos',
        element: <ProductCatalog />
      },
      {
        path: 'productos/:productoId',
        element: <Product />
      },
      {
        path: 'login',
        element: <LoginPage />
      },
      {
        path: 'carrito',
        element: <CarritoPage />
      },
      {
        path: 'boleta/:folio',
        element: <BoletaPage />
      }
    ]
  },
  {
    path: '/admin',
    element: <LayoutAdmin />,
    children: [
      {
        path: '/admin',
        index: true,
        element: <Saludo />
      },
      {
        path: '/admin/usuarios',
        element: <AdminUsers />
      },
      {
        path: '/admin/productos',
        element: <AdminProductos />
      }
    ]
  }
])
