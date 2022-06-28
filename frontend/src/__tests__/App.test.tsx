import React from 'react'
import { render } from '@testing-library/react'
import App from '../App'

test('.it should contain a Carousel component', async () => {
  const { getByTestId } = render(<App />)
  expect(getByTestId('Carousel')).toBeInTheDocument()
})
