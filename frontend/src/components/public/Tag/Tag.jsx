import { Link } from 'react-router-dom'
import './Tag.css'

export function Tag({ href, children }) {
    return <>
        <Link className='tag' to={href} rel="tag">{children}</Link >
    </>
}
