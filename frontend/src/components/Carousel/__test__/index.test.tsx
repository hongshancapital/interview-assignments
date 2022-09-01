import React from 'react'
import { act } from 'react-dom/test-utils';
import { fireEvent } from '@testing-library/react';
import { render } from './utils';
import Carousel from '../index'

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });
  it('渲染1个子项', () => {
    const {getByText} = render(<Carousel><div>I`m a carousel item.</div></Carousel>)
    const carouselItem = getByText('I`m a carousel item.')
    expect(carouselItem).toBeInTheDocument()
  })
 
  it('渲染多个子项, 子项数量是正确的，指示器数量是正确的', () => {
    const {getAllByText, find} = render(<Carousel><div>I`m a carousel item1.</div><div>I`m a carousel item2.</div><div>I`m a carousel item3.</div></Carousel>)
    const carouselItems = getAllByText(/I`m a carousel item/i)
    expect(carouselItems).toHaveLength(3)
    const indicators = find('.indicator-span')
    expect(indicators).toHaveLength(3)
  })

  it('自动轮播class和滚动距离是否正确', async () => {
    const wrapper = render(<Carousel><div>I`m a carousel item1.</div><div>I`m a carousel item2.</div><div>I`m a carousel item3.</div></Carousel>)
    expect(wrapper.find('.carousel-slide')[0]).toHaveClass('active')
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${0/3*100}%)`)
    act(() => {
      jest.advanceTimersByTime(4000);
    });
    expect(wrapper.find('.carousel-slide')[1]).toHaveClass('active')
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${1/3*100}%)`)
    act(() => {
      jest.advanceTimersByTime(8000);
    })
    expect(wrapper.find('.carousel-slide')[2]).toHaveClass('active')
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${2/3*100}%)`)
    act(() => {
      jest.advanceTimersByTime(12000);
    })
    expect(wrapper.find('.carousel-slide')[0]).toHaveClass('active')
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${0/3*100}%)`)
  })

  it('点击指示器,滚动到正确的位置，并重置定时器', () => {
    const wrapper = render(<Carousel delay={4000}><div>I`m a carousel item1.</div><div>I`m a carousel item2.</div><div>I`m a carousel item3.</div></Carousel>)
    fireEvent.click(wrapper.find('.indicator-span')[2])
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${2/3*100}%)`)
    act(() => {
      jest.advanceTimersByTime(4000);
    })
    expect(wrapper.find('.carousel-container')[0].style.transform).toEqual(`translateX(-${0/3*100}%)`)
  })
})
