import Carousel, { type CarouselProps } from '..'
import { render, fireEvent, act } from '@testing-library/react'

const items: CarouselProps['items'] = [
  {
    key: '1',
    render() {
      return <h1>Slide 1</h1>
    }
  },
  {
    key: '2',
    render() {
      return <h1>Slide 2</h1>
    }
  }
]

describe('Carousel', () => {
  jest.useFakeTimers()

  it('should render', () => {
    const { asFragment } = render(<Carousel items={items} />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('delay & loop', async () => {
    const { getByTestId } = render(<Carousel delay={3500} items={items} />)

    expect(getByTestId('carouse-item-0')).toHaveClass('active')
    expect(getByTestId('indicator-0')).toHaveClass('active')

    // Autoplay to slide 2 (index 1)
    act(() => {
      jest.runOnlyPendingTimers()
    })
    expect(getByTestId('carouse-item-1')).toHaveClass('active')
    expect(getByTestId('indicator-1')).toHaveClass('active')

    // Loop
    act(() => {
      jest.runOnlyPendingTimers()
    })
    expect(getByTestId('carouse-item-0')).toHaveClass('active')
    expect(getByTestId('indicator-0')).toHaveClass('active')
  })

  it('Handle indicator is clicked', () => {
    const { getByTestId } = render(<Carousel delay={3500} items={items} />)

    fireEvent.click(getByTestId('indicator-1'))
    expect(getByTestId('carouse-item-1')).toHaveClass('active')
    expect(getByTestId('indicator-1')).toHaveClass('active')

    fireEvent.click(getByTestId('indicator-0'))
    expect(getByTestId('carouse-item-0')).toHaveClass('active')
    expect(getByTestId('indicator-0')).toHaveClass('active')
  })

  it('change items', () => {
    const thisItems: CarouselProps['items'] = [
      ...items,
      {
        key: '3',
        render() {
          return <h1>Slide 3</h1>
        }
      }
    ]
    const { rerender, getByTestId } = render(<Carousel delay={3500} items={thisItems} />)
    // Active slide 3
    fireEvent.click(getByTestId('indicator-2'))
    // Delete Slide 3
    rerender(<Carousel delay={3500} items={items} />)
    // Should active slide 1 (index 0)
    expect(getByTestId('carouse-item-0')).toHaveClass('active')
    expect(getByTestId('indicator-0')).toHaveClass('active')
  })
})
