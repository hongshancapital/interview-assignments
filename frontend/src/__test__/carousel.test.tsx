import { fireEvent, render, screen, waitFor } from '@testing-library/react'
import { CSSProperties, createRef } from 'react'

import Carousel, { CarouselRef } from '../components/Carousel'

const imgs = [
  'https://images.unsplash.com/photo-1562886877-aaaa5c0b3225?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk5Mjc0fQ',
  'https://images.unsplash.com/photo-1572652963245-bd7fda887078?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk5Mjc0fQ',
  'https://images.unsplash.com/photo-1572656934803-d2162b2e98bf?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk5Mjc0fQ'
]

const divStyle: CSSProperties = {
  height: '400px',
  background: '#364d79'
}

const imgStyle: CSSProperties = {
  width: '100%',
  height: '100%'
}

describe('Carousel Test', () => {
  const ref = createRef<CarouselRef>()
  beforeEach(() => {
    render(
      <Carousel ref={ref}>
        {imgs.map((img: string, index: number) => (
          <div style={divStyle} key={index}>
            <img src={imgs[index]} style={imgStyle} alt={img} data-testid='img' />
          </div>
        ))}
      </Carousel>
    )
  })

  it('Carousel Visible test', async () => {
    expect(await screen.findByTestId('carousel')).toBeVisible()
  })

  it('Slide Count test', async () => {
    const slideContentCount = imgs.length
    const result = await screen.findAllByTestId('img')
    expect(result).toHaveLength(slideContentCount)
  })

  it('Click test', async () => {
    const allPaginationItems = await screen.findAllByTestId('pagination-item')
    fireEvent.click(allPaginationItems[0])
    await waitFor(async () => {
      expect(await screen.findByAltText(imgs[2])).toBeVisible()
    })
  })

  it('Should have prev,next and go function', async () => {
    const { prev, next, goTo } = ref.current as CarouselRef
    expect(typeof prev).toBe('function')
    expect(typeof next).toBe('function')
    expect(typeof goTo).toBe('function')
  })
})
