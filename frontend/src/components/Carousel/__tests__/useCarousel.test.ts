import { renderHook } from '@testing-library/react';
import { useCarousel, UseCarouselProps } from '../hooks/useCarousel';
import { waitFakeTimer } from '../../../tests/utils';
import { act } from 'react-dom/test-utils';

const setupHook = (initialProps: UseCarouselProps) =>
  renderHook(props => useCarousel(props), {
    initialProps
  });

describe('useCarousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should have a return value after render', () => {
    const initialProps = {
      slidersCount: 2,
      sliderWidth: 100
    };

    const { result } = setupHook(initialProps);

    const {
      activeIndex,
      isAnimating,
      trackStyle,
      slideTo,
      pausePlay,
      startPlay
    } = result.current;

    expect(typeof activeIndex).toBe('number');
    expect(typeof isAnimating).toBe('boolean');
    expect(typeof trackStyle).toBe('object');
    expect(typeof slideTo).toBe('function');
    expect(typeof pausePlay).toBe('function');
    expect(typeof startPlay).toBe('function');
  });

  it('should slide to the next slider at intervals when autoplay', async () => {
    const initialProps = {
      slidersCount: 2,
      sliderWidth: 100,
      duration: 3000,
      speed: 1000
    };

    const { result } = setupHook(initialProps);

    expect(result.current.activeIndex).toBe(0);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(0px)',
      transition: ''
    });

    await waitFakeTimer(3000, 1);
    expect(result.current.activeIndex).toBe(1);
    expect(result.current.isAnimating).toBe(true);
    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(-100px)',
      transition: 'transform 1000ms ease-in-out'
    });

    await waitFakeTimer(1000, 1);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(-100px)',
      transition: ''
    });

    await waitFakeTimer(2000, 1);
    expect(result.current.activeIndex).toBe(0);
    expect(result.current.isAnimating).toBe(true);
    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(0px)',
      transition: 'transform 1000ms ease-in-out'
    });

    await waitFakeTimer(1000, 1);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(0px)',
      transition: ''
    });
  });

  it('should change trackStyle every time the dependent props change', async () => {
    const initialProps = {
      slidersCount: 2,
      sliderWidth: 100,
      duration: 3000,
      speed: 500,
      autoplay: false
    };

    const { result, rerender } = setupHook(initialProps);

    expect(result.current.trackStyle).toEqual({
      width: '200px',
      transform: 'translateX(0px)',
      transition: ''
    });

    // sliderWidth changed
    initialProps.sliderWidth = 500;
    rerender(initialProps);
    expect(result.current.trackStyle).toEqual({
      width: '1000px',
      transform: 'translateX(0px)',
      transition: ''
    });

    // slidersCount changed
    initialProps.slidersCount = 3;
    rerender(initialProps);
    expect(result.current.trackStyle).toEqual({
      width: '1500px',
      transform: 'translateX(0px)',
      transition: ''
    });

    // speed changed
    act(() => result.current.slideTo(1));
    await waitFakeTimer(100, 1);
    expect(result.current.trackStyle).toEqual({
      width: '1500px',
      transform: 'translateX(-500px)',
      transition: 'transform 500ms ease-in-out'
    });

    initialProps.speed = 1000;
    rerender(initialProps);
    act(() => result.current.slideTo(1));
    await waitFakeTimer(100, 1);
    expect(result.current.trackStyle).toEqual({
      width: '1500px',
      transform: 'translateX(-500px)',
      transition: 'transform 1000ms ease-in-out'
    });
  });
});
