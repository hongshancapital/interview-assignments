import React from 'react'
import { render, act } from '@testing-library/react'
import Carousel from './index'

const WIDTH = '100px'
const HEIGHT = '100px'

// https://testing-library.com/docs/react-testing-library/api#cleanup

test('可以接收子元素', () => {
  const { getByText } = render(
    <Carousel>
      <div>1</div>
      <div>2</div>
      <div>3</div>
    </Carousel>
  )

  const text1 = getByText(/1/)
  const text2 = getByText(/2/)
  const text3 = getByText(/3/)
  expect(text1).toBeInTheDocument()
  expect(text2).toBeInTheDocument()
  expect(text3).toBeInTheDocument()
})

test('可以接收 prop: width 和 height', () => {
  const { container } = render(
    <Carousel width={WIDTH} height={HEIGHT}>
      <div>1</div>
    </Carousel>
  )
  // https://stackoverflow.com/a/53119366
  expect(container.firstChild).toHaveStyle(`width: ${WIDTH}; height: ${HEIGHT}`)
})

// https://juejin.cn/post/6873789504763723783
// https://kentcdodds.com/blog/fix-the-not-wrapped-in-act-warning
// https://zh-hans.reactjs.org/docs/testing-recipes.html#act
test('可以自动轮播，每一张默认显示时长 3 秒，同时底部对应的指示器会高亮', () => {
  const DURATION = 3000
  jest.useFakeTimers()
  const { container } = render(
    <Carousel width={WIDTH} height={HEIGHT}>
      <div>1</div>
      <div>2</div>
      <div>3</div>
    </Carousel>
  )
  const slidesElement = container.firstChild!.firstChild as HTMLElement
  const progressContainer = container.firstChild!.lastChild as HTMLElement
  // https://developer.mozilla.org/zh-CN/docs/Web/API/Document/querySelectorAll
  const p1 = progressContainer.querySelector(':scope div:nth-child(1) > div')!
  const p2 = progressContainer.querySelector(':scope div:nth-child(2) > div')!
  const p3 = progressContainer.querySelector(':scope div:nth-child(3) > div')!

  // 第一张
  expect(slidesElement.getAttribute('offset')).toEqual('0%')
  expect(p1.getAttribute('offset')).toEqual('0')
  expect(p2.getAttribute('offset')).toEqual('-100%')
  expect(p3.getAttribute('offset')).toEqual('-100%')
  expect(p1).toHaveStyle('visibility: visible')
  expect(p2).toHaveStyle('visibility: hidden')
  expect(p3).toHaveStyle('visibility: hidden')

  // 第二张
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('-100%')
  expect(p1.getAttribute('offset')).toEqual('-100%')
  expect(p2.getAttribute('offset')).toEqual('0')
  expect(p3.getAttribute('offset')).toEqual('-100%')
  expect(p1).toHaveStyle('visibility: hidden')
  expect(p2).toHaveStyle('visibility: visible')
  expect(p3).toHaveStyle('visibility: hidden')

  // 第三张
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('-200%')
  expect(p1.getAttribute('offset')).toEqual('-100%')
  expect(p2.getAttribute('offset')).toEqual('-100%')
  expect(p3.getAttribute('offset')).toEqual('0')
  expect(p1).toHaveStyle('visibility: hidden')
  expect(p2).toHaveStyle('visibility: hidden')
  expect(p3).toHaveStyle('visibility: visible')

  // 回到第一张
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('0%')
  expect(p1.getAttribute('offset')).toEqual('0')
  expect(p2.getAttribute('offset')).toEqual('-100%')
  expect(p3.getAttribute('offset')).toEqual('-100%')
  expect(p1).toHaveStyle('visibility: visible')
  expect(p2).toHaveStyle('visibility: hidden')
  expect(p3).toHaveStyle('visibility: hidden')

  jest.useRealTimers()
})

test('可以接收 props: duration 和 transitionDuration', async () => {
  jest.useFakeTimers()
  const DURATION = 10
  const { container } = render(
    <Carousel
      width={WIDTH}
      height={HEIGHT}
      duration={DURATION}
      transitionDuration={1}
    >
      <div>1</div>
      <div>2</div>
      <div>3</div>
    </Carousel>
  )
  const slidesElement = container.firstChild!.firstChild as HTMLElement
  expect(slidesElement.getAttribute('offset')).toEqual('0%')
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('-100%')
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('-200%')
  act(() => {
    jest.advanceTimersByTime(DURATION)
  })
  expect(slidesElement.getAttribute('offset')).toEqual('0%')
  jest.useRealTimers()
})
