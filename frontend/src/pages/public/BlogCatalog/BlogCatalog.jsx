import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { api_path } from "../../../util/apipath";
import { Spinner } from "../../../components/public/Spinner/Spinner";
import { BlogMiniature } from "../../../components/public/blog/BlogMiniature/BlogMiniature";
import "./BlogCatalog.css"

export function BlogCatalogPage() {
    const [searchParams] = useSearchParams();
    const [categoria, setCategoria] = useState(searchParams.get('categoria') || null);

    useEffect(() => {
        setCategoria(searchParams.get('categoria') || null);
    }, [searchParams])

    return <BlogCatalog categoria={categoria} />
}

export function BlogCatalog({ categoria, vertical, skip, texto }) {
    const [blogs, setBlogs] = useState([]);
    const [tituloDefault, setTituloDefault] = useState("Sabores, novedades y más")

    useEffect(() => {
        setBlogs([]);
        setTituloDefault("Sabores, novedades y más");
        const url = api_path(categoria ? `api/blogs?categoria=${categoria}` : 'api/blogs');
        const fetchData = async () => {
            const response = await fetch(url);
            if (response.ok) {
                const data = await response.json();
                setTimeout(() => {
                    setBlogs(data)
                    if (categoria && data.length > 0) {
                        const nombreCategoria = data[0].categoriaBlogNombre
                        setTituloDefault(`Tag: "${nombreCategoria}"`);
                    }
                }, 2000)
            }
        }
        fetchData();
    }, [categoria]);

    return <div className={vertical ? "blog-catalog vertical" : "blog-catalog"}>
        {blogs.length > 0 ? <div className="blogs">
            <h1 className="title">{texto ? texto : tituloDefault}</h1>
            <BlogList blogs={blogs} skip={skip} />
        </div> : <Spinner />}
    </div>
}

function BlogList({ blogs, skip }) {
    if (skip == null) skip = [];
    const content = blogs
        .filter(blog => !skip.includes(blog.id))
        .map(blog => <BlogMiniature key={blog.id} blog={blog} />)

    return <>{content}</>
}
