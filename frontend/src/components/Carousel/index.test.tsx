import React from 'react'
import { render } from '@testing-library/react'
import { Carousel, CarouselSlide } from './index'
import { act } from 'react-dom/test-utils'

describe('Carousel Component', () => {
  test('spreads the whole container', () => {
    const { container } = render(<Carousel><>1</></Carousel>)
    expect(container.firstChild).toHaveStyle('width: 100%; height: 100%')
  })
  
  test('has zero-width content if not given valid children', () => {
    const { container } = render(<Carousel><>1</></Carousel>)
    expect(container.firstChild?.firstChild).toHaveStyle('width: 0%')
  })
  
  test('has correct content width', () => {
    const { container: container1 } = render(
      <Carousel>
        <CarouselSlide>1</CarouselSlide>
      </Carousel>
    )
    expect(container1.firstChild?.firstChild).toHaveStyle('width: 100%')
  
    const { container: container2 } = render(
      <Carousel>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
        <CarouselSlide>3</CarouselSlide>
      </Carousel>
    )
    expect(container2.firstChild?.firstChild).toHaveStyle('width: 300%')
  })

  test('has correct content width', () => {
    const { container: container1 } = render(
      <Carousel>
        <CarouselSlide>1</CarouselSlide>
      </Carousel>
    )
    expect(container1.firstChild?.firstChild).toHaveStyle('width: 100%')
  
    const { container: container2 } = render(
      <Carousel>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
        <CarouselSlide>3</CarouselSlide>
      </Carousel>
    )
    expect(container2.firstChild?.firstChild).toHaveStyle('width: 300%')
  })

  test('can slide content left automatically', async () => {
    const duration = 0.01
    const { container } = render(
      <Carousel duration={duration}>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
        <CarouselSlide>3</CarouselSlide>
        <CarouselSlide>4</CarouselSlide>
      </Carousel>
    )
    const waitForOneDuration = async () => {
      await new Promise((resolve) => setTimeout(resolve, 1000 * duration));
    }
    expect(container.firstChild?.firstChild).toHaveStyle('transform: translateX(0%)')
    await act(waitForOneDuration)
    expect(container.firstChild?.firstChild).toHaveStyle('transform: translateX(-25%)')
    await act(waitForOneDuration)
    expect(container.firstChild?.firstChild).toHaveStyle('transform: translateX(-50%)')
    await act(waitForOneDuration)
    expect(container.firstChild?.firstChild).toHaveStyle('transform: translateX(-75%)')
    await act(waitForOneDuration)
    expect(container.firstChild?.firstChild).toHaveStyle('transform: translateX(0%)')
  })
})
