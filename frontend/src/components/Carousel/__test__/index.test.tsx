import React from 'react'
import { render, act } from '@testing-library/react'
import Carousel, { ICarouselRef } from '../index'

describe('Carousel Component Test', () => {
  test('Test Carousel Children and Dots Render', () => {
    const { container } = render(
      <Carousel dots={true} autoplay={false} className="test-carousel">
        <div />
        <div />
      </Carousel>,
    )
    // 测试className是否正常添加
    expect(container.querySelector('.com-carousel-wrap')).toHaveClass(
      'test-carousel',
    )
    expect(container.querySelectorAll('.com-carousel-item-box').length).toBe(2)
    expect(container.querySelectorAll('.com-dots-item').length).toBe(2)
  })
  test('Test Carousel Autoplay', () => {
    jest.useFakeTimers()
    const { container } = render(
      <Carousel autoplay={true} autoPlayInterval={2000}>
        <div />
        <div />
        <div />
      </Carousel>,
    )
    act(() => {
      jest.advanceTimersByTime(2000)
    })
    expect(container.querySelector('.com-carousel')).toHaveClass('com-offset1')
    act(() => {
      jest.advanceTimersByTime(2000)
    })
    expect(container.querySelector('.com-carousel')).toHaveClass('com-offset2')
    jest.useRealTimers()
  })
  test('Test Carousel Ref goTo Function', () => {
    jest.useFakeTimers()
    const ref = React.createRef<ICarouselRef>()
    const { container } = render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    )
    expect(typeof ref.current?.goTo).toBe('function')
    expect(container.querySelector('.com-carousel')).toHaveClass('com-offset0')
    act(() => {
      ref.current?.goTo(2)
    })
    expect(container.querySelector('.com-carousel')).toHaveClass('com-offset2')
    jest.useRealTimers()
  })
})
