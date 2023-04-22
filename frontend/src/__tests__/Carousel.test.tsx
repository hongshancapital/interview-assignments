import React from 'react';
import { render, fireEvent } from '@testing-library/react';

import Carousel from '../components/Carousel';
import { DEFAULT_ITEMS } from '../constants/config';

describe('Carousel', () => {
  test('renders three carousel items correctly', () => {
    const Component = () => (
      <Carousel items={DEFAULT_ITEMS} duration={3000} speed={400} />
    )
    const { container } = render(<Component />)
    expect(container).toMatchSnapshot()
    expect(container.querySelectorAll('.carousel-item')?.length).toBe(3)
    expect(container.querySelectorAll('.carousel-tab')?.length).toBe(3)
  })

  test('renders only one carousel item correctly', () => {
    const Component = () => (
      <Carousel items={DEFAULT_ITEMS.slice(0, 1)} duration={3000} speed={400} />
    )
    const { container } = render(<Component />)
    expect(container).toMatchSnapshot()
    expect(container.querySelectorAll('.carousel-item')?.length).toBe(1)
    expect(container.querySelectorAll('.carousel-tab')?.length).toBe(1)
  })

  test('renders no carousel item correctly', () => {
    const Component = () => (
      <Carousel items={[]} duration={3000} speed={400} />
    )
    const { container } = render(<Component />)
    expect(container).toMatchSnapshot()
    expect(container.querySelectorAll('.carousel-item')?.length).toBe(0)
    expect(container.querySelectorAll('.carousel-tab')?.length).toBe(0)
  })

  test('after click the carousel tab, the carousel item should be changed', () => {
    const Component = () => (
      <Carousel items={DEFAULT_ITEMS} duration={3000} speed={400} />
    )
    const { container } = render(<Component />)
    const allTabs = container.querySelectorAll('.carousel-tab') || [] as HTMLElement[]
    const firstTab = allTabs[0]
    const secondTab = allTabs[1]
    const thirdTab = allTabs[2]
    const itemsWrapper = container.querySelector('.carousel-items') as HTMLElement

    // initial state
    expect(itemsWrapper?.style?.transform).toBe('translateX(-0vw)')
    expect(firstTab?.children?.length).toBe(1)
    expect(secondTab?.children?.length).toBe(0)
    expect(thirdTab?.children?.length).toBe(0)

    // click the second tab
    fireEvent.click(secondTab)
    expect(itemsWrapper?.style?.transform).toBe('translateX(-100vw)')
    expect(firstTab?.children?.length).toBe(0)
    expect(secondTab?.children?.length).toBe(1)
    expect(thirdTab?.children?.length).toBe(0)

    // click the first tab
    fireEvent.click(firstTab)
    expect(itemsWrapper?.style?.transform).toBe('translateX(-0vw)')
    expect(firstTab?.children?.length).toBe(1)
    expect(secondTab?.children?.length).toBe(0)
    expect(thirdTab?.children?.length).toBe(0)

    // click the third tab
    fireEvent.click(thirdTab)
    expect(itemsWrapper?.style?.transform).toBe('translateX(-200vw)')
    expect(firstTab?.children?.length).toBe(0)
    expect(secondTab?.children?.length).toBe(0)
    expect(thirdTab?.children?.length).toBe(1)
  })
})
