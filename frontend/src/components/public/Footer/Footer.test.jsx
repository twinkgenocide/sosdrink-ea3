import React from 'react'
import { render, screen } from '@testing-library/react'
import { Footer } from './Footer'

describe('Footer', () => {
  it('renders contact info and social icons', () => {
    render(<Footer />)

    // Seccion de contacto
    expect(screen.getByText('Contacto')).toBeInTheDocument()
    expect(screen.getByText('+56 9 98563472')).toBeInTheDocument()
    expect(screen.getByText('sosdrink@gmail.com')).toBeInTheDocument()

    // Iconos de redes sociales
    const imgs = screen.getAllByRole('img')
    expect(imgs).toHaveLength(3)

    const links = screen.getAllByRole('link')
    expect(links.length).toBeGreaterThanOrEqual(3)
    links.forEach((link) => expect(link).toHaveAttribute('href'))
  })
})
