import { BlogOTM } from "../../../components/public/blog/BlogOTM/BlogOTM";
import { ProductCatalog } from "../ProductCatalog/ProductCatalog";

import './HomePage.css'

export function HomePage() {
    return <div className="home-container">
        <BlogOTM />
        <ProductCatalog />
    </div>
}
