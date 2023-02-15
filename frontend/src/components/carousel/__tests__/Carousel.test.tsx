import { act } from 'react-dom/test-utils';
import { render } from '@testing-library/react';
import Carousel from '../Carousel';

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should render Carousel component', () => {
    const { asFragment } = render(<Carousel />);
    expect(asFragment()).toMatchSnapshot();
  });

  it('should slide correct carousel card at intervals', () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.querySelector('.carousel-item__container')).toHaveStyle({
      transform: `translateX(-0%)`,
      transitionDuration: `1000ms`,
    });
    act(() => {
      jest.advanceTimersByTime(1000);
    });
    expect(container.querySelector('.carousel-item__container')).toHaveStyle({
      transform: `translateX(-100%)`,
      transitionDuration: `1000ms`,
    });
  });
});
