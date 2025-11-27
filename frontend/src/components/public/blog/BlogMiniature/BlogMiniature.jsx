import { useEffect, useState } from "react";
import { api_path } from "../../../../util/apipath"
import { Spinner } from "../../Spinner/Spinner";

import "./BlogMiniature.css"
import { Tag } from "../../Tag/Tag";
import { useNavigate } from "react-router-dom";

export function BlogMiniature({ blog }) {
    const [image, setImage] = useState();
    const url = api_path(blog.imagenUrl);
    const navigate = useNavigate();

    const fetchImage = async () => {
        const res = await fetch(url);
        const blob = await res.blob();
        setTimeout(() => {
            setImage(URL.createObjectURL(blob));
        }, Math.floor(Math.random() * 2000))
    }

    useEffect(() => {
        fetchImage();
    }, []);

    return <div className="blog-miniature" onClick={(e) => {
        if (e.target.closest("a")) return;
        navigate(`/blogs/${blog.id}`);
    }}>
        {
            image ?
                <BlogMiniContent blog={blog} image={image} />
                : <Spinner />
        }
    </div>
}

function BlogMiniContent({ blog, image }) {
    return <div className="content">
        <img src={image} />
        <div className="meta">
            <time dateTime={blog.fecha}>{new Date(blog.fecha).toLocaleString('es-CL', {
                weekday: 'short',
                day: 'numeric',
                month: 'short',
                year: 'numeric'
            })}</time>
            <Tag href={`/blogs?categoria=${blog.categoriaBlogId}`}>{blog.categoriaBlogNombre}</Tag>
        </div>
        <h3>{blog.titulo}</h3>
        <p>{blog.resumen}</p>
    </div>
}
