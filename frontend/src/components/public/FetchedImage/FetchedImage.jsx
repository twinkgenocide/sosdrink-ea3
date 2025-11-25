import { useEffect, useState } from "react";
import { Spinner } from "../Spinner/Spinner";

import "./FetchedImage.css";

export function FetchedImage({ src, className = "" }) {
    const [image, setImage] = useState(null);
    const [error, setError] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchImage = async () => {
            try {
                setLoading(true);
                setError(false);

                const res = await fetch(src);
                if (!res.ok) throw new Error();

                const blob = await res.blob();
                setTimeout(() => {
                    setImage(URL.createObjectURL(blob));
                    setLoading(false);
                }, Math.floor(Math.random() * 2000) + 1000);
            } catch {
                setError(true);
            }
        };

        fetchImage();
    }, [src]);

    let content;
    if (error) content = <div className="error" />;
    else if (loading) content = <Spinner />;
    else if (image) content = <img src={image} />

    return <figure className={`fetched-img ${className}`}>{content}</figure>
}
