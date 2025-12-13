import { useEffect, useState } from 'react'
import './BlogOTM.css'
import { api_path } from '../../../../util/apipath';
import { Link } from 'react-router-dom';
import { Spinner } from '../../Spinner/Spinner';
import { FetchedImage } from '../../FetchedImage/FetchedImage';
import { DatetimeBD } from '../../../shared/DatetimeBD';
import { Tag } from '../../Tag/Tag';

export function BlogOTM() {
    const [blog, setBlog] = useState(null);

    useEffect(() => {
        const url = api_path("api/blogs");
        const fetchBlog = async () => {
            const result = await fetch(url);
            const blogs = await result.json();
            setTimeout(() => {
                setBlog(blogs[
                    Math.floor(Math.random() * blogs.length)
                ]);
            }, 2000);
        };
        fetchBlog();
    }, []);

    return <div className='blog-otm-container'>
        {blog ? <BlogOTMDisplay blog={blog} /> : <Spinner />}
    </div>
}

function BlogOTMDisplay({ blog }) {
    console.log(blog);
    return <Link className='blog-otm' to={`/blogs/${blog.id}`}>
        <FetchedImage src={api_path(blog.imagenUrl)} />
        <div>
            <p>Blog destacado</p>
            <h1>{blog.titulo}</h1>
            <h3>{blog.resumen}</h3>
            <div className='meta'>
                <DatetimeBD datetime={blog.fecha} />
                <Tag href={`/blogs?categoria=${blog.categoriaBlogId}`}>{blog.categoriaBlogNombre}</Tag>
            </div>
        </div>
    </Link>
}
