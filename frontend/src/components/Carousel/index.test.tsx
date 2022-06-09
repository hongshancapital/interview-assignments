import React from 'react'
import { render, screen, act, fireEvent } from '@testing-library/react'
import { Carousel, CarouselSlide } from './index'

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
        <CarouselSlide>0</CarouselSlide>
      </Carousel>
    )
    expect(container1.firstChild?.firstChild).toHaveStyle('width: 100%')
  
    const { container: container2 } = render(
      <Carousel>
        <CarouselSlide>0</CarouselSlide>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
      </Carousel>
    )
    expect(container2.firstChild?.firstChild).toHaveStyle('width: 300%')
  })

  test('has correct content width', () => {
    const { container: container1 } = render(
      <Carousel>
        <CarouselSlide>0</CarouselSlide>
      </Carousel>
    )
    expect(container1.firstChild?.firstChild).toHaveStyle('width: 100%')
  
    const { container: container2 } = render(
      <Carousel>
        <CarouselSlide>0</CarouselSlide>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
      </Carousel>
    )
    expect(container2.firstChild?.firstChild).toHaveStyle('width: 300%')
  })

  test('can slide content left automatically', async () => {
    const duration = 0.01
    const { container } = render(
      <Carousel duration={duration}>
        <CarouselSlide>0</CarouselSlide>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
        <CarouselSlide>3</CarouselSlide>
      </Carousel>
    )
    const waitForOneDuration = async () => {
      await new Promise((resolve) => setTimeout(resolve, 1000 * duration));
    }
    const carouselContent = container.firstChild?.firstChild
    expect(carouselContent).toHaveStyle('transform: translateX(0%)')
    await act(waitForOneDuration)
    expect(carouselContent).toHaveStyle('transform: translateX(-25%)')
    await act(waitForOneDuration)
    expect(carouselContent).toHaveStyle('transform: translateX(-50%)')
    await act(waitForOneDuration)
    expect(carouselContent).toHaveStyle('transform: translateX(-75%)')
    await act(waitForOneDuration)
    expect(carouselContent).toHaveStyle('transform: translateX(0%)')
  })

  test('show indicators with the same amount as slides', async () => {
    render(
      <Carousel>
        <CarouselSlide>0</CarouselSlide>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
      </Carousel>
    )

    const indicators = await screen.findAllByRole('indicator')
    expect(indicators).toHaveLength(3)
  })

  test('can show the progress for the corresponding indicator and change slide when indicator is clicked', async () => {
    const duration = 0.01
    const { getByTestId } = render(
      <Carousel duration={duration}>
        <CarouselSlide>0</CarouselSlide>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
      </Carousel>
    )
    const waitForOneDuration = async () => {
      await new Promise((resolve) => setTimeout(resolve, 1000 * duration));
    }
    const ind0 = getByTestId('ind-0')
    const ind1 = getByTestId('ind-1')
    const ind2 = getByTestId('ind-2')

    // the firstChild of indicator is its progress-bar
    expect(ind0.firstChild).not.toBe(null)
    expect(ind1.firstChild).toBe(null)
    expect(ind2.firstChild).toBe(null)

    await act(waitForOneDuration)
    expect(ind0.firstChild).toBe(null)
    expect(ind1.firstChild).not.toBe(null)
    expect(ind2.firstChild).toBe(null)

    fireEvent.click(ind0)
    expect(ind0.firstChild).not.toBe(null)
    expect(ind1.firstChild).toBe(null)
    expect(ind2.firstChild).toBe(null)

    await act(waitForOneDuration)
    await act(waitForOneDuration)
    expect(ind0.firstChild).toBe(null)
    expect(ind1.firstChild).toBe(null)
    expect(ind2.firstChild).not.toBe(null)
  })
})
