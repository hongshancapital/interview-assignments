import { describe, expect, it } from 'vitest'
import React from 'react'
import Carousel from '@lib/index'
import { render, screen } from '../utils/test-utils'

describe('Carousel test', () => {
  it('works', () => {
    const nodes = (
      <Carousel >
        <div>Page1</div>
        <div>Page2</div>
        <div>Page3</div>
      </Carousel>
    )
    render(nodes)
    expect(screen.getByText('Page1')).toBeInTheDocument()
    expect(screen.getByText('Page2')).toBeInTheDocument()
    expect(screen.getByText('Page3')).toBeInTheDocument()
  })
})
