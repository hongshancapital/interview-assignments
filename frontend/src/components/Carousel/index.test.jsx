import React from 'react'
import { render, cleanup } from '@testing-library/react'
import Carousel from './index'

afterEach(cleanup)

test('Carousel without children', () => {
  const { container } = render(<Carousel></Carousel>)
  expect(container.querySelectorAll('.carousel')).toHaveLength(1)

  const carouselScroll = container.querySelectorAll('.carousel-scroll')
  expect(carouselScroll).toHaveLength(0)
  const carouselItems = container.querySelectorAll('.carousel-item')
  expect(carouselItems).toHaveLength(0)

  const carouselIndicator = container.querySelectorAll('.carousel-indicator')
  expect(carouselIndicator).toHaveLength(0)
})

test('Carousel with children', () => {
  const list = ['1', '2', '3']
  const { container } = render(<Carousel>
    {list.map((item, index) => <div key={index}>{item}</div>)}
  </Carousel>)
  expect(container.querySelectorAll('.carousel')).toHaveLength(1)

  const carouselScroll = container.querySelectorAll('.carousel-scroll')
  expect(carouselScroll).toHaveLength(1);
  const carouselItems = container.querySelectorAll('.carousel-item')
  expect(carouselItems).toHaveLength(3)

  const carouselIndicators = container.querySelectorAll('.carousel-indicator-item')
  expect(carouselIndicators).toHaveLength(3)
})

test('Carousel speed should work', () => {
  const list = ['1', '2', '3']
  const { container } = render(<Carousel speed={4000}>
    {list.map((item, index) => <div key={index}>{item}</div>)}
  </Carousel>)

  expect(container.querySelectorAll('.carousel-indicator-item.move')[0]).toHaveStyle('animation-duration: 4000ms')
  expect(container.querySelectorAll('.carousel-indicator-item.move')).toHaveLength(1)
})