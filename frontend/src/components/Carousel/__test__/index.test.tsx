import { render, act } from '@testing-library/react';
import Carousel from '../index';
describe('test Carousel component', () => {
  it('renders correctly', () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.firstChild).toMatchSnapshot();
  });

  it('mount correctly', () => {
    expect(() =>
      render(
        <Carousel>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      )
    ).not.toThrow();
  });

  it('test rendering defaults', () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    expect(container.querySelector('.slick-list')).toHaveStyle({
      transform: 'translateX(-0%)',
      transitionDuration: '1s',
    });

    expect(container.querySelectorAll('.progress')).toHaveLength(3);
  });
  it('test rendering properties', () => {
    const { container } = render(
      <Carousel speed={2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    expect(container.querySelector('.slick-list')).toHaveStyle({
      transform: 'translateX(-0%)',
      transitionDuration: '2s',
    });
  });

  it('test when speed is greater than duration', () => {
    const { container } = render(
      <Carousel speed={4} duration={2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    expect(container.querySelector('.slick-list')).toHaveStyle({
      transform: 'translateX(-0%)',
      transitionDuration: '2s',
    });
  });
  it('test the click event', () => {
    const { container } = render(
      <Carousel speed={4} duration={2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    expect(container.querySelector('.slick-list')).toHaveStyle({
      transform: 'translateX(-0%)',
      transitionDuration: '2s',
    });
    const dot = container.querySelectorAll('.carousel-dots .progress-bar')[2];

    act(() => {
      dot.dispatchEvent(new MouseEvent('click', { bubbles: true }));
    });

    expect(container.querySelector('.slick-list')).toHaveStyle({
      transform: 'translateX(-200%)',
      transitionDuration: '2s',
    });
  });
  describe('test autoplay', () => {
    beforeAll(() => {
      jest.useFakeTimers();
    });

    afterAll(() => {
      jest.useRealTimers();
    });

    afterEach(() => {
      jest.clearAllTimers();
    });
    it('test the autoplay timer', () => {
      const { container } = render(
        <Carousel>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      );
      expect(container.querySelector('.slick-list')).toHaveStyle({
        transform: 'translateX(-0%)',
        transitionDuration: '1s',
      });

      act(() => {
        jest.advanceTimersByTime(100);
      });
      expect(container.querySelector('.slick-list')).toHaveStyle({
        transform: 'translateX(-0%)',
        transitionDuration: '1s',
      });

      act(() => {
        jest.advanceTimersByTime(3000);
      });
      expect(container.querySelector('.slick-list')).toHaveStyle({
        transform: 'translateX(-100%)',
        transitionDuration: '1s',
      });

      act(() => {
        jest.advanceTimersByTime(3000);
      });
      expect(container.querySelector('.slick-list')).toHaveStyle({
        transform: 'translateX(-200%)',
        transitionDuration: '1s',
      });

      act(() => {
        jest.advanceTimersByTime(3000);
      });

      expect(container.querySelector('.slick-list')).toHaveStyle({
        transform: 'translateX(-0%)',
        transitionDuration: '1s',
      });
    });
  });
});
