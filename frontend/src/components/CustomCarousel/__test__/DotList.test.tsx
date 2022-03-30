import React from 'react'
import { render } from '@testing-library/react'

import DotList from '../DotList'

test('dotlist len correct', () => {
  const component = render(<DotList len={4} activeIndex={0} />)
  const len = component.getAllByTestId('item').length
  expect(len).toBe(4)
})

test('dotlist activeIndex correct', () => {
  const component = render(<DotList len={3} activeIndex={1} />)
  const activeEl = component.getAllByTestId('item')[1]
  activeEl.classList.contains('active')
})