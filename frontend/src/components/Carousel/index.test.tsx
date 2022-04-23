import React from "react";
import { render, screen, fireEvent } from '@testing-library/react'
import { Carousel } from ".";
import { Content } from "../Content";

const items = [
  {
    color: '#fff',
    backgroundColor: '#111',
    img: require('../../assets/iphone.png'),
    title: 'xPhone',
    subtitle: ['Lots to love.Less to spend.', 'Starting as $399.']
  },
  {
    color: '#000',
    backgroundColor: '#fafafa',
    img: require('../../assets/tablet.png'),
    title: 'Tablet',
    subtitle: ['Just the right amount of everything.']
  },
  {
    color: '#000',
    backgroundColor: '#f1f1f3',
    img: require('../../assets/airpods.png'),
    title: ['Buy a Tablet Or xPhone for college.', 'Get airPods.'],
    subtitle: []
  }
]

describe('carousel', () => {
  test('快照', () => {
    const { container } = render(
      <Carousel auto indicator control width={800} height={400}>
        {items.map((item, index) => <Content key={index} {...item} />)}
      </Carousel>
    )
    expect(container).toMatchSnapshot()
  })

  test('点击左侧按钮', async () => {
    render(
      <Carousel auto indicator control width={800} height={400}>
        {items.map((item, index) => <Content key={index} {...item} />)}
      </Carousel>
    )
    const ele = await screen.findByTestId('elements')
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(-800px, 0, 0)')
    const next = await screen.findByTestId('next')
    fireEvent.click(next)
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(-1600px, 0, 0)')
  })

  test('点击右侧按钮', async () => {
    render(
      <Carousel auto indicator control width={800} height={400}>
        {items.map((item, index) => <Content key={index} {...item} />)}
      </Carousel>
    )
    const ele = await screen.findByTestId('elements')
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(-800px, 0, 0)')
    const prev = await screen.findByTestId('prev')
    fireEvent.click(prev)
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(0px, 0, 0)')
  })

  test('下方导航点击', async () => {
    render(
      <Carousel auto indicator control width={800} height={400}>
        {items.map((item, index) => <Content key={index} {...item} />)}
      </Carousel>
    )
    const ele = await screen.findByTestId('elements')
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(-800px, 0, 0)')
    const indicator = await screen.findByTestId('indicator_1')
    fireEvent.click(indicator)
    expect(ele.style.getPropertyValue('transform')).toBe('translate3d(-1600px, 0, 0)')
  })
})