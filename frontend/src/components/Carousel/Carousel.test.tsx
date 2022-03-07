import React from 'react';
import { render, act, fireEvent } from '@testing-library/react';
import Carousel from './index'

function wait(time: number) {
  return new Promise(resolve => {
    setTimeout(resolve, time)
  })
}

describe('Carousel Component', () => {
  const interval = 1500
  const speed = 100
  let carouselEl: HTMLDivElement
  let containerEl: HTMLDivElement
  let indicatorsEl: HTMLUListElement

  const data = [
    { id: 1, text: 'First' },
    { id: 2, text: 'Second' },
  ]

  beforeEach(() => {
    const { getByTestId } = render(
      <Carousel interval={interval} speed={speed}>
        {
          data.map(i => (
            <Carousel.Item key={i.id}>
              <p>{i.text}</p>
            </Carousel.Item>
          ))
        }
      </Carousel>
    )

    carouselEl = getByTestId('carousel') as HTMLDivElement
    if (carouselEl) {
      ;[containerEl, indicatorsEl] = Array.from(carouselEl.children) as [HTMLDivElement, HTMLUListElement]
    }
  })


  /**
   * 轮播容器及指示器元素是否存在且子元素个数渲染是否正确
   */
  test('Container and Indicators', () => {
    expect(containerEl).toBeDefined()
    expect(containerEl.children.length).toBe(data.length)
    expect(indicatorsEl).toBeDefined()
    expect(indicatorsEl.children.length).toBe(data.length)
  })

  /**
   * 测试第一张轮播图是否正常展示
   */
  test('First slide content', () => {
    const firstSlide = containerEl.children[0]
    expect(firstSlide.textContent).toBe('First')
  })

  /**
   * 测试第二张轮播图是否正常展示
   */
  test('Second slide content', () => {
    const secondSlide = containerEl.children[1]
    expect(secondSlide.textContent).toBe('Second')
  })

  /**
   * 开始轮播
   */
  test('Start carousel', async () => {
    await act(async () => {
      expect(containerEl.style.transform).toBe(`translateX(-0px)`)

      await wait(interval + 10)
      expect(containerEl.style.transform).toBe(`translateX(-${containerEl.clientWidth}px)`)

      await wait(interval + 10)
      expect(containerEl.style.transform).toBe(`translateX(-0px)`)
    })
  }, 10000)

  /**
   * 点击指示器跳转
   */
  test('Click indicator', async () => {
    await act(async () => {
      const [, secondIndicatorEl] = Array.from(indicatorsEl.children) as HTMLLIElement[]
      fireEvent.click(secondIndicatorEl)
      await wait(speed + 10)
      expect(containerEl.style.transform).toBe(`translateX(-${containerEl.clientWidth}px)`)
    })
  })
})
