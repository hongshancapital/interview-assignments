import React from 'react';
import { render } from '@testing-library/react';

import CarouselItem from '../components/CarouselItem';

describe('CarouselItem', () => {
  test('renders mock data correctly', () => {
    const Component = () => (
      <CarouselItem
        title="mock-title"
        description="mock-description"
        background="mock-background"
      />
    )
    const { container } = render(<Component />)
    const item = container.querySelector('.carousel-item')
    const title = container.querySelector('.carousel-item-title')
    const description = container.querySelector('.carousel-item-description')

    expect(container).toMatchSnapshot()
    expect(item?.classList?.contains('mock-background-background')).toBe(true)
    expect(title?.textContent).toBe('mock-title')
    expect(description?.textContent).toBe('mock-description')
  })
})
