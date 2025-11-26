import React from 'react'
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { render, screen, fireEvent, waitFor } from '@testing-library/react'

// Mock del useParams mientras se mantienen exportaciones de react-router-dom
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual('react-router-dom')
  return {
    ...actual,
    useParams: () => ({ productoId: '1' }),
  }
})

// Mock de la api_path para que devuelva el mismo path que se le pasa
vi.mock('../../../util/apipath', () => ({ api_path: (p) => p }))

// Mock de ProductCatalog para evitar renderizados pesados o redes adicionales
vi.mock('../ProductCatalog/ProductCatalog', () => ({ ProductCatalog: ({ texto }) => <div data-testid="related">{texto}</div> }))

import { Product } from './Product'

describe('Product page', () => {
  const originalFetch = global.fetch

  beforeEach(() => {
    vi.useFakeTimers()
  })

  afterEach(() => {
    vi.useRealTimers()
    global.fetch = originalFetch
    vi.resetAllMocks()
  })


  it('muestra el spinner, luego los detalles del producto y permite añadir/quitar del carrito', async () => {
    const product = {
      id: 1,
      nombre: 'Producto de prueba',
      precio: 1234,
      detalle: 'Descripcion del producto',
      imagen: '/uploads/product.png',
      tipoProducto: { id: 2 }
    }

    global.fetch = vi.fn(() => Promise.resolve({ ok: true, json: () => Promise.resolve(product) }))

    const { container } = render(<Product />)


    expect(screen.queryByText(product.nombre)).toBeNull()

    vi.runAllTimers()


    await waitFor(() => expect(screen.getByText(product.nombre)).toBeInTheDocument())


    const img = container.querySelector('img')
    expect(img).toBeTruthy()
    expect(img.getAttribute('src')).toContain(product.imagen)


    expect(screen.getByText(`$${product.precio}`)).toBeInTheDocument()
    expect(screen.getByText(product.detalle)).toBeInTheDocument()


    const addBtn = screen.getByText('Añadir al carrito')
    expect(addBtn).toBeInTheDocument()
    fireEvent.click(addBtn)

    
    expect(screen.getByText('Eliminar del carrito')).toBeInTheDocument()

    fireEvent.click(screen.getByText('Eliminar del carrito'))
    expect(screen.getByText('Añadir al carrito')).toBeInTheDocument()
  })
})
