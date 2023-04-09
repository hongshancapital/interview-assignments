import { render,act } from '@testing-library/react';
import Carousel from '../components/Carousel';

describe('Carousel', () => {
  it('should render and autoPlay', () => {
    jest.useFakeTimers();
    const { getAllByTestId } = render(
      <div data-testid="root">
        <Carousel delay={3000} speed={300} count={3}>
          <div data-testid="slide">
            <Carousel.Slide>123</Carousel.Slide>
            <Carousel.Slide>456</Carousel.Slide>
            <Carousel.Slide>789</Carousel.Slide>
          </div>
        </Carousel>
      </div>,
    );
    act(() => {
      jest.runOnlyPendingTimers();
    });

    const root = getAllByTestId('root');
    const slide = getAllByTestId('slide');
    expect(root[0]).toContainHTML(`<div class="carousel__slide ">123</div>`);
    expect(root[0]).toContainElement(slide[0]);
  });
});
