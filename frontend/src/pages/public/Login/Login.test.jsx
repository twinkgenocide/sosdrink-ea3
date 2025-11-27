import React from 'react'
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'

// Mock del useNavigate
const mockNavigate = vi.fn()
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual('react-router-dom')
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  }
})

// Mock del api_path para que devuelva el mismo path que se le pasa
vi.mock('../../../util/apipath', () => ({ api_path: (p) => p }))

import { LoginPage } from './Login'

describe('LoginPage', () => {
  const originalFetch = globalThis.fetch
  const originalAlert = window.alert

  beforeEach(() => {
    mockNavigate.mockClear()
    window.alert = vi.fn()
  })

  afterEach(() => {
    globalThis.fetch = originalFetch
    window.alert = originalAlert
    vi.resetAllMocks()
  })


  it('renderiza el formulario de login con campos de email y password', () => {
    render(<LoginPage />)

    expect(screen.getByText('Bienvenid@ de vuelta!')).toBeInTheDocument()
    expect(screen.getByLabelText(/correo/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/clave/i)).toBeInTheDocument()
    expect(screen.getByRole('button', { name: /iniciar sesion/i })).toBeInTheDocument()
  })


  it('muestra error de validacion cuando el formulario se envia con campos vacios', async () => {
    const user = userEvent.setup()
    globalThis.fetch = vi.fn()
    render(<LoginPage />)

    const submitBtn = screen.getByRole('button', { name: /iniciar sesion/i })
    await user.click(submitBtn)

    //El formulario deberia no enviarse si los campos estan vacios (validacion HTML5)

    expect(globalThis.fetch).not.toHaveBeenCalled()
  })


  
  it('redirije a /admin cuando un usuario admin inicia sesion exitosamente', async () => {
    const user = userEvent.setup()
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ tipoUsuario: { nombre: 'administrador' } })
      })
    )

    render(<LoginPage />)

    const emailInput = screen.getByLabelText(/correo/i)
    const passwordInput = screen.getByLabelText(/clave/i)
    const submitBtn = screen.getByRole('button', { name: /iniciar sesion/i })

    await user.type(emailInput, 'admin@test.com')
    await user.type(passwordInput, 'password123')
    await user.click(submitBtn)

    await waitFor(() => {
      expect(globalThis.fetch).toHaveBeenCalledWith(
        'api/usuarios/login',
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ correo: 'admin@test.com', clave: 'password123' })
        })
      )
    })

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('/admin')
    })
  })

  

  it('redirije a /productos cuando un usuario regular inicia sesion exitosamente', async () => {
    const user = userEvent.setup()
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ tipoUsuario: { nombre: 'usuario' } })
      })
    )

    render(<LoginPage />)

    const emailInput = screen.getByLabelText(/correo/i)
    const passwordInput = screen.getByLabelText(/clave/i)
    const submitBtn = screen.getByRole('button', { name: /iniciar sesion/i })

    await user.type(emailInput, 'user@test.com')
    await user.type(passwordInput, 'password123')
    await user.click(submitBtn)

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('/productos')
    })
  })

  

  it('muestra una alerta cuando el login falla', async () => {
    const user = userEvent.setup()
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: false
      })
    )

    render(<LoginPage />)

    const emailInput = screen.getByLabelText(/correo/i)
    const passwordInput = screen.getByLabelText(/clave/i)
    const submitBtn = screen.getByRole('button', { name: /iniciar sesion/i })

    await user.type(emailInput, 'wrong@test.com')
    await user.type(passwordInput, 'wrongpass')
    await user.click(submitBtn)

    await waitFor(() => {
      expect(window.alert).toHaveBeenCalledWith('Usuario o contraseña incorrecta!')
    })
    expect(mockNavigate).not.toHaveBeenCalled()
  })
})
