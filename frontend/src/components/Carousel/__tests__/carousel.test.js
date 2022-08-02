import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import Carousel from '../index'
import CarouselItem from '../carousel-item'
import img0 from '../../../assets/ios16.jpeg'
import img1 from '../../../assets/macos.jpeg'
import img2 from '../../../assets/persion.jpeg'

// 它应该包含一个CarouselItem组件
test('Carousel: 1.它应该包含一个CarouselItem组件', async () => {
  const { getByTestId } = await render(
    <Carousel>
      <CarouselItem>
        <img src={img1} />
      </CarouselItem>
    </Carousel>)
  expect(getByTestId('CarouselItem')).toBeInTheDocument()
})

test('Carousel: 2.如果子组件不是CarouselItem类型，则给出throw error阻断性警告', async () => {
  const errmsg = '发生错误：子节点必须是CarouselItem'
  try {
    const { getByLabelText } = await render(
      <>
        <Carousel aria-label="Carousel">
          <div>the demo text</div>
        </Carousel >
        <div id="componentType-error">
          {errmsg}
        </div>
      </>)
  } catch (err) {
    expect(err.message).toEqual(errmsg)
  }
})


test('Carousel:3.当用户点击指示按钮2时，img2应该可见', async () => {
  const { getByTestId } = await render(
    <Carousel>
      <CarouselItem>
        <img src={img0} />
      </CarouselItem>
      <CarouselItem>
        <img src={img1} />
      </CarouselItem>
      <CarouselItem data-testid='CarouselItem2'>
        <img src={img2} />
      </CarouselItem>
    </Carousel>)
  fireEvent.click(getByTestId('indicator2'))
  expect(getByTestId('CarouselItem2')).toBeVisible()
})

test('Carousel:4.当用户点击指示按钮2时，指示器按钮应该含有样式“on”', async () => {
  const { getByTestId } = await render(
    <Carousel>
      <CarouselItem>
        <img src={img0} />
      </CarouselItem>
      <CarouselItem>
        <img src={img1} />
      </CarouselItem>
      <CarouselItem data-testid='CarouselItem2'>
        <img src={img2} />
      </CarouselItem>
    </Carousel>)
  fireEvent.click(getByTestId('indicator2'))
  expect(getByTestId('indicator2')).toHaveClass('on')
})

