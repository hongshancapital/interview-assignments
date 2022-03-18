import * as React from 'react'
import { render, screen } from '@testing-library/react'
import Carousel from '../index.tsx'

describe('Carousel', () => {
  test('renders App component', () => {
    const { container } = render(
      <Carousel>
        <div>page1</div>
        <div>page2</div>
        <div>page3</div>
      </Carousel>
    )
    // 校验内容存在
    expect(screen.getByText('page1')).toBeInTheDocument()
    expect(screen.getByText('page2')).toBeInTheDocument()
    expect(screen.getByText('page3')).toBeInTheDocument()

    // 校验底部缩略点元素还在
    expect(container.querySelectorAll('.carousel__dot__item').length).toEqual(3)
    expect(container.querySelectorAll('.carousel__dot__item__line--active').length).toEqual(1)
  })
})