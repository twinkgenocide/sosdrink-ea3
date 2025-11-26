import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import React from 'react'
import { render, screen, fireEvent, waitFor } from '@testing-library/react'

// Mock del useNavigate mientras se mantienen exportaciones de react-router-dom
const mockNavigate = vi.fn()
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual('react-router-dom')
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  }
})

import { MemoryRouter } from 'react-router-dom'
import { BlogMiniature } from './BlogMiniature'

describe('BlogMiniature', () => {
  const originalCreateObjectURL = URL.createObjectURL
  const originalFetch = global.fetch

  beforeEach(() => {
    vi.useFakeTimers()
    mockNavigate.mockClear()

    global.fetch = vi.fn(() => Promise.resolve({
      blob: () => Promise.resolve(new Blob(['img'], { type: 'image/png' }))
    }))

    // mock de createObjectURL
    URL.createObjectURL = vi.fn(() => 'blob:fake')
  })

  afterEach(() => {
    vi.useRealTimers()
    URL.createObjectURL = originalCreateObjectURL
    global.fetch = originalFetch
  })

  it('shows spinner first, then loads image and content, and navigates on click', async () => {
    const blog = {
      id: 42,
      fecha: '2023-01-01T00:00:00Z',
      categoriaBlog: { id: 1, nombre: 'Cat' },
      titulo: 'Título de prueba',
      resumen: 'Resumen de prueba',
      imagenUrl: '/uploads/img.png'
    }

    const { container } = render(
      <MemoryRouter>
        <BlogMiniature blog={blog} />
      </MemoryRouter>
    )


    expect(screen.queryByRole('img')).toBeNull()

    // Temporizador rapido para que el setTimeout en fetchImage se ejecute
    vi.runAllTimers()

    // Esperar a que la imgen se cargue
    await waitFor(() => expect(screen.getByRole('img')).toBeInTheDocument())

    // Content should be present
    expect(screen.getByText(blog.titulo)).toBeInTheDocument()
    expect(screen.getByText(blog.resumen)).toBeInTheDocument()
    expect(screen.getByText(blog.categoriaBlog.nombre)).toBeInTheDocument()

    // Simular click y verificar navegacion
    const mini = container.querySelector('.blog-miniature')
    expect(mini).toBeTruthy()
    fireEvent.click(mini)

    expect(mockNavigate).toHaveBeenCalledWith(`/blogs/${blog.id}`)
  })
})
