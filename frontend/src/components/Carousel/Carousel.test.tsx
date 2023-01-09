import { render } from '@testing-library/react';
import Carousel, { carouselMockData } from '.';

describe('<Carousel />', () => {
  it('should display the first slide when first rendered', () => {
    const { getByTestId, container } = render(
      <Carousel contents={carouselMockData} />
    );
    expect(getByTestId('animation-dot-0')).toHaveClass('actived');
    expect(container.getElementsByClassName('slider')[0]).toHaveStyle(
      'transform: translateX(-0%)'
    );
  });
});
