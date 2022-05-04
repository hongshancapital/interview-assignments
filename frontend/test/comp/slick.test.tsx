import { describe, expect, it } from 'vitest'
import React from 'react'
import Slick from '@lib/Slick'
import { render } from '../utils/test-utils'

describe('Carousel Slick test', () => {
  it('running animation', () => {
    const count = 3
    render(<Slick count={count} step={1} duration={3000} />)
    const nodes = document.querySelectorAll('.carousel-lite__dot')
    expect(nodes.length).toBe(count)
    expect(nodes[0].classList.contains('running')).toBe(false)
    expect(nodes[1].classList.contains('running')).toBe(true)
    expect(nodes[2].classList.contains('running')).toBe(false)
  })
})
