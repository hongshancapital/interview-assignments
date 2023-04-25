import React from 'react'
import { render } from '@testing-library/react'
import { act } from 'react-dom/test-utils'
import Carousel from './index'
import { IData } from "../../interface";

const getData = (num: number): IData[] => {
  return Array(num).fill({
    imgUrl: '',
    color: "#FFFFFF",
    title: "test",
    description: "test",
    price: "test",
    bgColor: "#111"
  })
}
// 测试数据准确性
test('Carousel data length equals to 3', () => {
  let carouselLength = 3
  const { container } = render(<Carousel data={getData(carouselLength)} duration={0} />)

  expect(container.querySelectorAll('.carouselItem').length).toBe(carouselLength)
  expect(container.querySelectorAll('.dotProgress').length).toBe(carouselLength)
  expect(container.querySelectorAll('.dotActive').length).toBe(1)
})

// 测试轮播图运行情况
beforeEach(() => {
  jest.useFakeTimers()
})
test('Carousel loop', () => {
  const { container } = render(<Carousel data={getData(3)} duration={0} />)

  expect(container.querySelector('.carouselContainer').style.transform).toMatch('translateX(-0vw)')

  act(() => {
    jest.runOnlyPendingTimers()
  })

  expect(container.querySelector('.carouselContainer').style.transform).toMatch('translateX(-100vw)')

  act(() => {
    jest.runOnlyPendingTimers()
  })

  expect(container.querySelector('.carouselContainer').style.transform).toMatch('translateX(-200vw)')

  act(() => {
    jest.runOnlyPendingTimers()
  })

  expect(container.querySelector('.carouselContainer').style.transform).toMatch('translateX(-0vw)')
})