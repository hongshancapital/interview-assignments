import React from 'react'
import { render } from '@testing-library/react'
import Carousel from './index'

test('renders children contents', async () => {
  const carousel = render(
    <Carousel>
      <div data-testid="1">1</div>
    </Carousel>
  )
  const banner1 = await carousel.findByTestId('1')
  expect(banner1.textContent).toContain('1')
})
