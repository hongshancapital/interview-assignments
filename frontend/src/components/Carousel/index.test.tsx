import React from 'react'
import { render, fireEvent, screen } from '@testing-library/react'
import Comp from './index'

test('renders component:Carousel', async () => {
  const emptyDom = <div></div>
  const contents = [emptyDom, emptyDom, emptyDom]
  const renderElement = render(<Comp contents={contents} />)
  const carouselCells = document?.getElementsByClassName(
    'custom-carousel-single-container',
  )
  expect(carouselCells.length).toEqual(3)
  const container = screen.getByTestId('custom-carousel-container')
  fireEvent.click(screen.getByTestId('test-carousel-dot-0'))
  expect(container.style.transform).toEqual('translateX(-0%)')
  fireEvent.click(
    screen.getByTestId(`test-carousel-dot-${carouselCells.length - 1}`),
  )
  expect(container.style.transform).toEqual(
    `translateX(-${((contents.length - 1) * 100) / contents.length}%)`,
  )
})
