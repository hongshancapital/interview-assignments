import React from 'react';
import { render, fireEvent } from '@testing-library/react';

import CarouselTab from '../components/CarouselTab';

describe('CarouselTab', () => {
  test('renders mock inactive data correctly', () => {
    const Component = () => (
      <CarouselTab
        index={4}
        onChange={(index: number) => {}}
        isActive={false}
        duration={3000}
      />
    )
    const { container } = render(<Component />)
    const tab = container.querySelector('.carousel-tab')

    expect(container).toMatchSnapshot()
    expect(tab?.children?.length).toBe(0)
  })

  test('renders mock active data correctly', () => {
    const Component = () => (
      <CarouselTab
        index={4}
        onChange={(index: number) => {}}
        isActive
        duration={3000}
      />
    )
    const { container } = render(<Component />)
    const tab = container.querySelector('.carousel-tab')

    expect(container).toMatchSnapshot()
    expect(tab?.children?.length).toBe(1)
    expect((tab?.children?.[0] as HTMLDivElement)?.style?.animationDuration).toBe('3000ms')
  })

  test('calls onChange when clicked', () => {
    const mockOnChange = jest.fn()
    const Component = () => (
      <CarouselTab
        index={4}
        onChange={mockOnChange}
        isActive
        duration={3000}
      />
    )
    const { container } = render(<Component />)
    const tab = container.querySelector('.carousel-tab') as HTMLDivElement

    fireEvent.click(tab)

    expect(mockOnChange).toHaveBeenCalledTimes(1)
    expect(mockOnChange).toHaveBeenCalledWith(4)
  })
})
