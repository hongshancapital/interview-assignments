import React from 'react'
import { cleanup, render } from '@testing-library/react'
import Carousel from './Carousel'
import { act } from 'react-dom/test-utils'

describe('Carousel', () => {
  beforeEach(cleanup)

  const mockData = Array(2).fill('')

  test('render item num correctly', () => {
    const { container } = render(
      <Carousel data={mockData} renderItem={() => null} />
    )
    const items = container.getElementsByClassName('carousel-item')
    expect(items.length).toBe(2)
    const indicators = container.getElementsByClassName('carousel-indicator-item')
    expect(indicators.length).toBe(2)
  })

  test('run carousel animation collectly', async () => {
    const { container } = render(
      <Carousel data={mockData} duration={800} renderItem={() => null} />
    )
    const listEl = container.getElementsByClassName('carousel-list')[0]
    const indicators = container.getElementsByClassName('carousel-indicator-item')
    expect(indicators.length).toBe(2)
    expect(listEl).toHaveStyle({
      width: '200%',
      transform: `translate3d(0%, 0, 0)`,
    })
    await act(async () => {
      await delay(1000)
    })
    expect(listEl).toHaveStyle({
      width: '200%',
      transform: `translate3d(-50%, 0, 0)`,
    })
    expect(indicators[0]).toHaveClass('carousel-indicator-item-filled')
    expect(indicators[1]).toHaveClass('carousel-indicator-item-active')
    await act(async () => {
      await delay(1000)
    })
    expect(listEl).toHaveStyle({
      width: '200%',
      transform: `translate3d(0%, 0, 0)`,
    })
  })

  test('disable autoPlay', async () => {
    const { container } = render(
      <Carousel data={mockData} duration={800} autoPlay={false} renderItem={() => null} />
    )
    const listEl = container.getElementsByClassName('carousel-list')[0]
    expect(listEl).toHaveStyle({
      width: '200%',
      transform: `translate3d(0%, 0, 0)`,
    })
    await act(async () => {
      await delay(1000)
    })
    expect(listEl).toHaveStyle({
      width: '200%',
      transform: `translate3d(0%, 0, 0)`,
    })
  })
})

function delay(t: number) {
  return new Promise((resolve) => setTimeout(resolve, t))
}
