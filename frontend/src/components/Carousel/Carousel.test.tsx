import React from 'react';
import { render, act } from '@testing-library/react';
import { assets } from './options'
import Carousel from './index'

function wait(time: number) {
  return new Promise(resolve => {
    setTimeout(resolve, time)
  })
}

describe('Carousel Component', () => {
  const interval = 1500
  let carouselEl: HTMLDivElement
  let containerEL: HTMLDivElement
  let indicatorsEl: HTMLUListElement

  beforeEach(() => {
    const { getByTestId } = render(<Carousel interval={interval} />)
    carouselEl = getByTestId('carousel') as HTMLDivElement

    if (carouselEl) {
      ;[containerEL, indicatorsEl] = Array.from(carouselEl.children) as [HTMLDivElement, HTMLUListElement]
    }
  })


  /**
   * 轮播容器及指示器元素是否存在且子元素个数渲染是否正确
   */
  test('Container and Indicators', () => {
    expect(containerEL).toBeDefined()
    expect(containerEL.children.length).toBe(assets.length)
    expect(indicatorsEl).toBeDefined()
    expect(indicatorsEl.children.length).toBe(assets.length)
  })

  /**
   * 测试第一张轮播图是否正常展示
   */
  test('First slide content', () => {
    const firstSlide = containerEL.children[0]
    const [adsEl, bgEl] = Array.from(firstSlide.children)

    expect(adsEl?.textContent).toBe('Buy a Tablet or XPhone for collerge.Get arPords - airpods')

    expect(bgEl).toBeDefined()
  })

  /**
   * 测试第二张轮播图是否正常展示
   */
  test('Second slide content', () => {
    const secondSlide = containerEL.children[1]
    const [adsEl, bgEl] = Array.from(secondSlide.children)

    expect(adsEl?.textContent).toBe('Get arPords - iphone')

    expect(bgEl).toBeDefined()
  })

  /**
   * 测试第三张轮播图是否正常展示
   */
  test('Third slide content', () => {
    const thirdSlide = containerEL.children[2]
    const [adsEl, bgEl] = Array.from(thirdSlide.children)

    expect(adsEl?.textContent).toBe('Get arPords - tablet')

    expect(bgEl).toBeDefined()
  })


  /**
   * 开始轮播
   */
  test('Start carousel', async () => {
    await act(async () => {
      const [firstIndicatorEl, secondIndicatorEl, thirdIndicatorEl] = Array.from(indicatorsEl.children) as [HTMLLIElement, HTMLLIElement, HTMLLIElement]
      expect(containerEL.style.transform).toBe(`translateX(-0px)`)
      expect(firstIndicatorEl.querySelector('span')).toBeInTheDocument()
      
      await wait(interval + 10)
      expect(containerEL.style.transform).toBe(`translateX(-${containerEL.clientWidth}px)`)
      expect(secondIndicatorEl.querySelector('span')).toBeInTheDocument()

      await wait(interval + 10)
      expect(containerEL.style.transform).toBe(`translateX(-${containerEL.clientWidth * 2}px)`)
      expect(thirdIndicatorEl.querySelector('span')).toBeInTheDocument()

      await wait(interval + 10)
      expect(containerEL.style.transform).toBe(`translateX(-0px)`)
      expect(firstIndicatorEl.querySelector('span')).toBeInTheDocument()
    })
  }, 10000)
})
