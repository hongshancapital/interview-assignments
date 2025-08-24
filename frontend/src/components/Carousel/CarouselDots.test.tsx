import { render, fireEvent } from '@testing-library/react'
import CarouselDots, { CarouselDotsProps } from './CarouselDots'
import { SwiperTrigger } from './useSwiper'

describe('CarouselDots', () => {
  const onClick: SwiperTrigger = jest.fn()
  const props: CarouselDotsProps = {
    activeIndex: 0,
    size: 3,
    onClick,
  }

  afterEach(() => {
    jest.resetAllMocks()
  })

  it('renders correctly', () => {
    const { container } = render(<CarouselDots {...props} />)
    expect(container.querySelector('.carousel-dots')?.children.length).toBe(
      props.size
    )
  })

  it('calls onClick when clicked', () => {
    const { container } = render(<CarouselDots {...props} />)
    fireEvent.click(container.querySelector('.carousel-dot') as Element)
    expect(onClick).toHaveBeenCalledWith(0)
  })

  it('highlights active dot', () => {
    const { container } = render(<CarouselDots {...props} />)
    const activeDot = container.querySelector('.carousel-dot.active')
    expect(activeDot).toBeInTheDocument()
  })

  it('sets the animation direction style', () => {
    const duration = 5
    const { container } = render(
      <CarouselDots {...props} duration={duration} />
    )
    const dot = container.querySelector('.carousel-dot') as HTMLElement
    expect(dot.style.animationDirection).toBe(`${duration}s`)
  })
})
