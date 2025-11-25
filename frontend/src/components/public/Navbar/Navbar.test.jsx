import {render, screen} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import Navbar from './Navbar'
import { expect } from 'vitest'

describe('Navbar', () => {

    it('muestra marca y links con sus href correctos', () => {
        render(
            <MemoryRouter>
                <Navbar />
            </MemoryRouter>
        )

        const brand = screen.getByRole('link', { name: /sosdrink-ea3/i } )
        expect(brand).toBeInTheDocument()
        expect(brand).toHaveAttribute('href', '/')

        expect(screen.getByRole('link', { name: /home/i })).toHaveAttribute('href', '/')
        expect(screen.getByRole('link', { name: /blogs/i })).toHaveAttribute('href', '/blogs')
        expect(screen.getByRole('link', { name: /productos/i })).toHaveAttribute('href', '/productos')

        const toggler = screen.getByRole('button')
        expect(toggler).toHaveAttribute('data-bs-toggle', 'collapse')
        expect(toggler).toHaveAttribute('data-bs-target', '#menuNav')

    })

    it ('navega a las rutas correctas al hacer click en los links', async () => {
        const user = userEvent.setup()

        render(
            <MemoryRouter initialEntries={['/']}>
                <Navbar />
                <Routes>
                    <Route path='/' element={<h1>Home</h1>} />
                    <Route path='/blog' element={<h1>Blogs</h1>} />
                    <Route path='/producto' element={<h1>Productos</h1>} />
                </Routes>
            </MemoryRouter>
        )

        await user.click(screen.getByRole('link', { name: /blogs/i }))
        expect(await screen.findByRole('heading', { name: /blogs/i })).toBeInTheDocument()
    })

})



























