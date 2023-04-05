import { render } from '@testing-library/react';
import Carousel from '../components/Carousel';

describe('Carousel', () => {
  it('should render', () => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        <Carousel count={1}>
          <div data-testid="slide">
            <Carousel.Slide>123</Carousel.Slide>
          </div>
        </Carousel>
      </div>,
    );
    const root = getAllByTestId('root');
    const slide = getAllByTestId('slide');
    expect(root[0]).toContainHTML(`<div class="carousel-slide ">123</div>`);
    expect(root[0]).toContainElement(slide[0]);
  });
});
