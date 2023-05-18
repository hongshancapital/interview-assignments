/** @format */

import React from 'react'
import { render, screen } from '@testing-library/react'
import Carousel from './Carousel'

class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
}

const TestCarousel = () => (
  <div style={{ width: '500px', height: '500px' }}>
    <Carousel>
      {[1, 2, 3].map((num) => (
        <div key={num}>{num}</div>
      ))}
    </Carousel>
  </div>
)

test('find the carousel', () => {
  window.ResizeObserver = ResizeObserver
  render(<TestCarousel></TestCarousel>)
  const app = screen.getByTestId('wrapper')
  expect(app).toBeInTheDocument()
  expect(screen.getByText('3')).toBeInTheDocument()
})
