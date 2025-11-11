import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { api_path } from "../../../util/apipath";
import { Spinner } from "../../../components/public/Spinner/Spinner";
import { Blog } from "../../../components/public/Blog/Blog";

import "./BlogPage.css"
import { BlogError } from "../../../components/public/Blog/BlogError";

export function BlogPage() {
    const { blogId } = useParams();
    const [blogData, setBlogData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const url = api_path(`api/blogs/${blogId}`);
        const fetchBlog = async () => {
            try {
                setLoading(true);
                setError(false);
                const response = await fetch(url);
                if (!response.ok) throw new Error(response.status);
                const data = await response.json();
                setBlogData(data);
            } catch (e) {
                setError(e.message)
            } finally {
                setLoading(false);
            }
        }

        setTimeout(fetchBlog, 2000);
    }, [blogId]);

    const content = () => {
        if (loading) return <Spinner />
        if (error) return <BlogError code={error} />
        if (!blogData) return <p>Blog no encontrado</p>
        return <Blog data={blogData} />
    }

    return <div className="blog-container">{content()}</div>
}
