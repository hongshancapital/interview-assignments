import App from './App'
import React from 'react'
import { render } from '@testing-library/react'
import userEvent from '@testing-library/user-event'

describe('验证Carousel组件逻辑：', () => {
  test('点击切换', async() => {
    const { container,queryByTestId } = render(<App />)
    const clickDomList = container.querySelectorAll(".click-wrapper")
  
    // 点击第二个元素
    await userEvent.click(clickDomList[1])
    // 第二个被选中
    expect(queryByTestId("click-wrapper-1")).toHaveClass("active")
  })

})