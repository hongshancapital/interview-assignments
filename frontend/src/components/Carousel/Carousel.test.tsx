import { render } from '@testing-library/react'
import Carousel from './Carousel'

describe('CarouselSlide', () => {
  it('should have className', () => {
    const { getAllByTestId } = render(
      <div data-testid="slide">
        <Carousel.Slide className="test-classname">123</Carousel.Slide>
      </div>
    )
    const slide = getAllByTestId('slide')
    expect(slide[0].firstChild).toHaveClass('test-classname')
  })
})
