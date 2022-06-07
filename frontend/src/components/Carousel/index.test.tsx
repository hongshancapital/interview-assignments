import React from 'react'
import { render } from '@testing-library/react'
import {Carousel} from './index'

test('Carousel spreads the whole container', () => {
  const { container } = render(<Carousel images={[]} />)
  expect(container.firstChild).toHaveStyle('width: 100%; height: 100%')
})
