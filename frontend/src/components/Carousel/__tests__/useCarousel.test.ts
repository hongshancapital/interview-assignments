/**
 * @jest-environment jsdom
 */

import { renderHook } from '@testing-library/react';
import { useCarousel, UseCarouselProps } from '../hooks/useCarousel';
import { waitFakeTimer } from '../../../tests/utils';
import { act } from 'react-dom/test-utils';
import '@testing-library/jest-dom/extend-expect';

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
      duration: 3000,
      speed: 1000
    };

    const { result } = setupHook(initialProps);

    expect(result.current.activeIndex).toBe(0);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(0%)',
      transition: ''
    });

    await waitFakeTimer(3000, 1);
    expect(result.current.activeIndex).toBe(1);
    expect(result.current.isAnimating).toBe(true);
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(-50%)',
      transition: 'transform 1000ms ease-in-out'
    });

    await waitFakeTimer(1000, 1);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(-50%)',
      transition: ''
    });

    await waitFakeTimer(2000, 1);
    expect(result.current.activeIndex).toBe(0);
    expect(result.current.isAnimating).toBe(true);
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(0%)',
      transition: 'transform 1000ms ease-in-out'
    });

    await waitFakeTimer(1000, 1);
    expect(result.current.isAnimating).toBe(false);
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(0%)',
      transition: ''
    });
  });

  it('should change trackStyle every time the dependent props change', async () => {
    const initialProps = {
      slidersCount: 2,
      duration: 3000,
      speed: 500,
      autoplay: false
    };

    const { result, rerender } = setupHook(initialProps);

    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(0%)',
      transition: ''
    });

    // slidersCount changed
    initialProps.slidersCount = 4;
    rerender(initialProps);
    act(() => result.current.slideTo(1));
    await waitFakeTimer()
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(-25%)',
      transition: ''
    });

    // speed changed
    initialProps.speed = 1000;
    rerender(initialProps);
    act(() => result.current.slideTo(2));
    await waitFakeTimer(100, 1)
    expect(result.current.trackStyle).toEqual({
      transform: 'translateX(-50%)',
      transition: 'transform 1000ms ease-in-out'
    });
  });
});
