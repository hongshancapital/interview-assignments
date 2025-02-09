import { createRoot } from "react-dom/client"
import { ReactNode, ReactElement } from "react"
import { act } from "@testing-library/react"
import Carousel from './carousel'

jest.setTimeout(20000)

const getCarouselItem = (total: number, duration: number = 3): ReactElement => {
  const array = new Array<ReactNode>(total).fill(null)
  return (
    <Carousel duration={duration}>
      {array.map((_, index) => <Carousel.Item key={index}><p>test</p></Carousel.Item>)}
    </Carousel>
  )
}

const sleep = (duration: number) => {
  return new Promise(res => {
    setTimeout(res, duration * 1000)
  })
}

let container: HTMLDivElement
beforeEach(() => {
  container = document.createElement('div')
  document.body.appendChild(container)
})

afterEach(() => {
  document.body.removeChild(container)
})

let total: number,
    duration: number = 3,
    carouselCount: number

it('测试Carousel组件:空轮播图', () => {
  total = 0
  act(() => {
    createRoot(container).render(getCarouselItem(total, duration))
  })
  carouselCount = container.querySelectorAll('.j-carousel-item').length
  expect(carouselCount).toBe(0)
})

it('测试Carousel组件:轮播图数量为3', () => {
  total = 3
  act(() => {
    createRoot(container).render(getCarouselItem(total, duration))
  })
  carouselCount = container.querySelectorAll('.j-carousel-item').length
  expect(carouselCount).toBe(3)
})

it('测试Carousel组件:轮播图滚动', async () => {
  total = 3, duration = 4
  act(() => {
    createRoot(container).render(getCarouselItem(total, duration))
  })
  const carouselItemCon = container.querySelector('.j-carousel>div') as HTMLDivElement
  expect(carouselItemCon.style.left).toBe('-0%')
  
  for(let i=1; i<=total; i++) {
    await act(async () => {
      await sleep(duration)
    })
    expect(carouselItemCon.style.left).toBe(`-${100*(i%total)}%`)
  }
})