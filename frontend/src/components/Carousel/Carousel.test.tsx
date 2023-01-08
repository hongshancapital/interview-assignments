import { render, act } from '@testing-library/react';
import Carousel, { carouselMockData } from '.';

describe('<Carousel />', () => {
  it('should display the first slide when first rendered', () => {
    jest.useFakeTimers();
    jest.spyOn(global, 'setInterval');
    const { container } = render(<Carousel contents={carouselMockData} />);
    const dots = container.querySelectorAll('.dot');
    expect(dots[0]).toHaveClass('actived');
    expect(setInterval).toBeCalledTimes(1);
    jest.useRealTimers();
  });

  it('should call the setInterval again and display the second slide when the given interval time is reached', () => {
    jest.useFakeTimers();
    jest.spyOn(global, 'setInterval');
    const interval = 3;
    const { container } = render(
      <Carousel contents={carouselMockData} interval={interval} />
    );
    act(() => {
      jest.advanceTimersByTime(interval * 1000);
    });
    act(() => expect(setInterval).toBeCalledTimes(2));
    act(() => {
      const dots = container.querySelectorAll('.dot');
      expect(dots[1]).toHaveClass('actived');
    });
    jest.useRealTimers();
  });
});
