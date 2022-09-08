import React from 'react'
import { act } from 'react-dom/test-utils';
import { fireEvent, within } from '@testing-library/react';
import { render } from './utils';
import Carousel from '../index'
import {listData, BannerItem} from '../__demo__'

describe('Carousel', () => {
  // 复用demo中数据
  const listDataNodes = listData.map(item => (
    <BannerItem {...item} key={item.id}/>
  ))

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
    const { find } = render(<Carousel>{listDataNodes}</Carousel>)
    const slides = find('.carousel-slide') as NodeListOf<HTMLElement>

    // 轮播元素有3个
    expect(slides).toHaveLength(3)
    // 每一个元素是否包含各自该有的文字
    within(slides[0]).getByText('XPhone')
    within(slides[1]).getByText('Tablet')
    within(slides[2]).getByText(/Buy a Tablet or xPhone for college./m)
    // 指示器是否有3个
    expect(find('.indicator-span')).toHaveLength(3)
  })

  it('自动轮播class和滚动距离是否正确', async () => {
    const {querySelector, find} = render(<Carousel>{listDataNodes}</Carousel>)
    const container = querySelector('.carousel-container')
    const slides = find('.carousel-slide')

    expect(slides[0]).toHaveClass('active')
    expect(container?.style.transform).toEqual(`translateX(-${0/3*100}%)`)

    act(() => {
      jest.advanceTimersByTime(4000);
    });
    expect(slides[1]).toHaveClass('active')
    expect(container?.style.transform).toEqual(`translateX(-${1/3*100}%)`)

    act(() => {
      jest.advanceTimersByTime(8000);
    })
    expect(slides[2]).toHaveClass('active')
    expect(container?.style.transform).toEqual(`translateX(-${2/3*100}%)`)

    // 滚动到底后，是否倒退回滚到第一个元素
    act(() => {
      jest.advanceTimersByTime(12000);
    })
    expect(slides[0]).toHaveClass('active')
    expect(container?.style.transform).toEqual(`translateX(-${0/3*100}%)`)
  })

  it('点击指示器，滚动到正确的位置，选中正确的指示器，并确定重置定时器', () => {
    const {querySelector, find} = render(<Carousel delay={4000}>{listDataNodes}</Carousel>)
    const container = querySelector('.carousel-container')
    const indicators = find('.indicator-span')

    fireEvent.click(indicators[2])
    expect(container?.style.transform).toEqual(`translateX(-${2/3*100}%)`)
    expect(indicators[2]).toHaveClass('active')
    
    act(() => {
      jest.advanceTimersByTime(4000);
    })
    expect(container?.style.transform).toEqual(`translateX(-${0/3*100}%)`)
    expect(indicators[0]).toHaveClass('active')
  })
})
