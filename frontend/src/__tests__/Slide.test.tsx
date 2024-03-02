import { render } from '@testing-library/react';
import CarouselSlide from '../components/Carousel/components/Slide';

describe('CarouselSlide', () => {
  it('should render', () => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        <CarouselSlide className="slide">test123</CarouselSlide>
      </div>,
    );
    const root = getAllByTestId('root');
    expect(root[0]).toContainHTML(
      `<div class="carousel-slide slide">test123</div>`,
    );
  });

  it('can get className', () => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        <CarouselSlide className="slide">test123</CarouselSlide>
      </div>,
    );
    const root = getAllByTestId('root');
    expect(root[0].firstChild).toHaveClass('slide');
  });
});
