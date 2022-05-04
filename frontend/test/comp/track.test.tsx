import { describe, expect, it } from 'vitest'
import React from 'react'
import Track from '@lib/Track'
import { render, screen } from '../utils/test-utils'

describe('Carousel Track test', () => {
  it('one child visible', () => {
    const oneChild = (<div>child</div>)
    render(<Track children={oneChild} step={0} width={1440} />)
    expect(screen.getByText('child')).toBeInTheDocument()
  })
  it('multiple child visible', () => {
    const children = ([<div>child1</div>, <div>child2</div>, <div>child3</div>])
    render(<Track children={children} step={0} width={1440} />)
    expect(document.querySelector('.carousel-lite__slides')?.children.length)
      .toBe(children.length)
    expect(screen.getByText('child1')).toBeInTheDocument()
    expect(screen.getByText('child2')).toBeInTheDocument()
    expect(screen.getByText('child3')).toBeInTheDocument()
  })
  it('transform works', () => {
    const oneChild = (<div>child</div>)
    render(<Track children={oneChild} step={1} width={1440} />)
    const node = document.getElementsByClassName('carousel-lite__slides')?.[0]
    expect(node).not.toBeUndefined()
    expect((node as HTMLElement).style.transform).toBe('translateX(-1440px)')
  })
})

describe('Carousel Slick test', () => {
  it('one child visible', () => {
    const oneChild = (<div>child</div>)
    render(<Track children={oneChild} step={0} width={1440} />)
    expect(screen.getByText('child')).toBeInTheDocument()
  })
})
