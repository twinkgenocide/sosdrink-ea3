import Markdown from "react-markdown";
import { DatetimeBD } from "../../shared/DatetimeBD";
import { Tag } from "../Tag/Tag";
import { Disclaimer } from "../Disclaimer/Disclaimer";
import { FetchedImage } from "../FetchedImage/FetchedImage";
import { api_path } from "../../../util/apipath";

import "./Blog.css"

export function Blog({ data }) {
    return <>
        <article className="blog">
            <FetchedImage src={api_path(data.imagenUrl)} className="blog-image" />
            <header className="blog-header">
                <h1>{data.titulo}</h1>
                <h3>{data.resumen}</h3>
                <div className="inline">
                    <DatetimeBD datetime={data.fecha} />
                    <Tag href={`/blogs?categoria=${data.categoriaBlogId}`}>{data.categoriaBlogNombre}</Tag>
                </div>
            </header>
            <hr />
            <section className="blog-content">
                <Markdown>{data.contenido}</Markdown>
                <Disclaimer />
            </section>
        </article>
    </>
}
