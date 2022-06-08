import React from 'react'
import { render } from '@testing-library/react'
import App from './App'

test('App Component renders in document', () => {
  const { container } = render(<App />)
  expect(container).toBeInTheDocument()
})

test('App Component renders expected presentation pages', () => {
  const { getByText } = render(<App />)
  expect(getByText('xPhone')).toBeInTheDocument()
  expect(getByText('Lots to love. Less to spend.')).toBeVisible()

  expect(getByText('Tablet')).toBeInTheDocument()
  expect(getByText('Get arPods.')).toBeInTheDocument()
})
