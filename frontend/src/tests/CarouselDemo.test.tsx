import React from 'react';
import { render, cleanup, act, fireEvent } from '@testing-library/react';
import { Carousel } from '../compoents/molecules'
import { CarouselDemo } from '../compoents/templates'

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers()
  })

  afterEach(() => {
    cleanup()
    jest.useRealTimers()
  })

  it('Basic usage', () => {
    const { container } = render(<Carousel />);
    expect(container.getElementsByClassName('carousel-container').length).toEqual(1)
    expect(container.getElementsByClassName('slide-container').length).toEqual(1)
    expect(container.getElementsByClassName('slide-item').length).toEqual(0)
  })

  it('AutoPlay', () => {
    const { container, rerender } = render(<CarouselDemo/>)
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-100%, 0,0); transition: transform 1000ms;">');
    act(() => {
      jest.advanceTimersByTime(3000);
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-200%, 0,0); transition: transform 1000ms;">')
    act(() => {
      jest.advanceTimersByTime(3000);
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-300%, 0,0); transition: transform 1000ms;">')
    act(() => {
      rerender(<CarouselDemo autoPlay={false} />)
    });
    act(() => {
      jest.advanceTimersByTime(3000);
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-300%, 0,0); transition: transform 1000ms;">')
  })

  it('ShowIndicators', () => {
    const { container, rerender } = render(<CarouselDemo />);
    expect(container.getElementsByClassName('carousel-indicator-container').length).toEqual(1)
    rerender(<CarouselDemo showIndicators={false} />)
    expect(container.getElementsByClassName('carousel-indicator-container').length).toEqual(0)
  })

  it('HoverPause', () => {
    const { container } = render(<CarouselDemo/>)
    act(() => {
      const containerEl = container.querySelector('.carousel-container')
      if (containerEl) {
        fireEvent.mouseEnter(containerEl)
      }
      jest.advanceTimersByTime(4000)
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-100%, 0,0); transition: transform 1000ms;">')
  })

  it('ClickChangeIdx', () => {
    const { container } = render(<CarouselDemo/>)
    act(() => {
      const indicatorItem = container.getElementsByClassName('indicator-item')[1]
      if (indicatorItem) {
        fireEvent.click(indicatorItem)
      }
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-200%, 0,0); transition: transform 1000ms;">')
    act(() => {
      const indicatorItem = container.getElementsByClassName('indicator-item')[2]
      if (indicatorItem) {
        fireEvent.click(indicatorItem)
      }
    })
    expect(container.getElementsByClassName('slide-container')[0]).toContainHTML('<div class="slide-container" style="transform: translate3d(-300%, 0,0); transition: transform 1000ms;">')
  })
})