import React from 'react'
import { render } from '@testing-library/react'
import App from './App'


test('Render App with Carousel', () => {
  const { container } = render(<App />)
  expect(container.querySelectorAll('.carousel')).toHaveLength(1)
})
