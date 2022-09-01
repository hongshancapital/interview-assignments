import React from 'react'
import { fireEvent } from '@testing-library/react';
import { render } from './utils';
import Indicator from '../indicator'

describe('Indicator', () => {
  const onDotClick = jest.fn()
  it('渲染指定数量的指示器正确', () => {
    const {find} = render(<Indicator activeIndex={1} length={3} delay={4000} onDotClick={onDotClick} />)
    const indicators = find('.indicator-span')
    expect(indicators).toHaveLength(3)
  })

  it('选中的指示器正确', () => {
    const activeIndex = 2
    const {find} = render(<Indicator activeIndex={activeIndex} length={3} delay={4000} onDotClick={onDotClick} />)
    const indicators = find('.indicator-span')
    expect(indicators[activeIndex]).toHaveClass('active')
  })

  
  it('点击指示器回调参数正确', () => {
    const clickIndex = 3
    const {find} = render(<Indicator activeIndex={2} length={6} delay={4000} onDotClick={onDotClick} />)
    const indicators = find('.indicator-span')
    fireEvent.click(indicators[clickIndex])
    expect(onDotClick).toBeCalledTimes(1)
    expect(onDotClick).toHaveBeenCalledWith(clickIndex)
  })
})
