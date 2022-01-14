import React from 'react'
import { render, screen } from '@testing-library/react'
import App from './App'

test('renders learn react link', () => {
  render(<App />)
  setTimeout(() => {
    expect(screen.getByText(/xPhone/)).toBeInTheDocument()
    expect(screen.getByText(/Tablet/)).toBeInTheDocument()
    expect(
      screen.getByText(/Buy a Table or xPhone for colleg/)
    ).toBeInTheDocument()
  }, 100)
})
