import React from 'react'
import { render } from '@testing-library/react'
import App from './App'

test('render App with Carousel', () => {
  const { getByText } = render(<App />)
  const linkElement = getByText(/Get arPods/i)
  expect(linkElement).toBeInTheDocument()
})
