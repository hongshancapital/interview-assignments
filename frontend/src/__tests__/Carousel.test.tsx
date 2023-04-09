import { render,act } from '@testing-library/react';
import Carousel from '../components/Carousel';

const TEST_DATA = [123,456,789];
describe('Carousel', () => {
  it('should render and autoPlay', () => {
    jest.useFakeTimers();
    const { getAllByTestId } = render(
      <div data-testid="root">
        <Carousel delay={3000} speed={300} count={3}>
          <div data-testid="slide">
            {TEST_DATA.map(el => <Carousel.Slide key={el}>{el}</Carousel.Slide>)}
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
