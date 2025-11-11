export function DatetimeBD({ datetime }) {
    return <time dateTime={datetime}>
        {new Date(datetime).toLocaleString('es-CL', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        })}
    </time>
}
