import React from 'react'
import { render, waitFor, fireEvent } from '@testing-library/react'
import {Carousel} from './index'

const delay = (time: number) => new Promise(resolve => setTimeout(resolve, time))

test('duration not set', async () => {
  const {container}  = render((
    <Carousel>
      <div>0</div>
      <div>1</div>
    </Carousel>
  ))

  const content = container.getElementsByClassName('carousel__content')[0] as HTMLDivElement

  await waitFor(async () => {
    await delay(300)
    expect(content.style.transform).toBe('translateX(0%)')
  })
});

test('duration', async () => {
  const {container}  = render((
    <Carousel duration={500}>
      <div>0</div>
      <div>1</div>
    </Carousel>
  ))

  const content = container.getElementsByClassName('carousel__content')[0] as HTMLDivElement

  expect(content.style.transform).toBe('translateX(0%)')

  await waitFor(async () => {
    await delay(500)
    expect(content.style.transform).toBe('translateX(-100%)')
  })

  await waitFor(async () => {
    await delay(500)
    expect(content.style.transform).toBe('translateX(-200%)')
    // 触发关键动画结束事件
    fireEvent.transitionEnd(content)
  })
  expect(content.style.transform).toBe('translateX(0%)')

  await waitFor(async () => {
    await delay(500)
    expect(content.style.transform).toBe('translateX(-100%)')
  })
});

test('default active', async () => {
  const {container}  = render((
    <Carousel defaultActive={1}>
      <div>0</div>
      <div>1</div>
    </Carousel>
  ))

  const content = container.getElementsByClassName('carousel__content')[0] as HTMLDivElement

  await waitFor(async () => {
    await delay(300)
    expect(content.style.transform).toBe('translateX(-100%)')
  })
});

test('show indicator',  () => {
  const {container}  = render((
    <Carousel showIndicator={false}>
      <div>0</div>
      <div>1</div>
    </Carousel>
  ))

  const indicators = container.getElementsByClassName('carousel__indicators')

  expect(indicators.length).toBe(0)
});
