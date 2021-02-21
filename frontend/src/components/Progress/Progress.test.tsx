import React from 'react'
import { render } from '@testing-library/react'
import Progress from './index'

test('Progress 组件存在，且内部元素默认偏移 -100%，且不可见', () => {
  const { container } = render(<Progress />)
  const inner = container.firstChild!.firstChild as HTMLElement
  expect(inner.getAttribute('offset')).toEqual('-100%')
  expect(inner).toHaveStyle('visibility: hidden')
})

test('可以接收 prop: active', () => {
  const { container, rerender } = render(<Progress />)
  const inner = container.firstChild!.firstChild as HTMLElement
  expect(inner.getAttribute('offset')).toEqual('-100%')
  expect(inner).toHaveStyle('visibility: hidden')

  // https://testing-library.com/docs/example-update-props/
  rerender(<Progress active={true} />)
  expect(inner.getAttribute('offset')).toEqual('0')
  expect(inner).toHaveStyle('visibility: visible')
})

test('可以接收 prop: duration，默认值 3000', async () => {
  const DURATION = 1
  const { container, rerender } = render(<Progress />)
  const inner = container.firstChild!.firstChild as HTMLElement
  expect(inner).toHaveStyle('transition: transform 3000ms linear')

  rerender(<Progress duration={DURATION} />)
  expect(inner).toHaveStyle(`transition: transform ${DURATION}ms linear`)
})
