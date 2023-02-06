/**
 * @jest-environment jsdom
 */

import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import Carousel, { CarouselRef } from '../Carousel';
import { waitFakeTimer, windowResize } from '../../../tests/utils';
import '@testing-library/jest-dom/extend-expect';

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should render Carousel component', () => {
    const { container } = render(
      <Carousel>
        <div />
      </Carousel>
    );
    expect(container).toBeInTheDocument();
  });

  it('should render the correct number of indicators', () => {
    const { container, rerender } = render(<Carousel />);
    expect(container.querySelectorAll('.indicator').length).toBe(0);

    // Update children
    rerender(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.querySelectorAll('.indicator').length).toBe(3);
  });

  it('should active slider when children change', () => {
    const { rerender, container } = render(<Carousel />);
    expect(container.querySelector('.slider-active')).toBeFalsy();

    // Update children
    rerender(
      <Carousel>
        <div />
      </Carousel>
    );
    expect(container.querySelector('.slider-active')).toBeTruthy();
  });

  it('should have goTo, goPrev and goNext function and work normally', async () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    const { goTo, goPrev, goNext } = ref.current || {};

    expect(typeof goTo).toBe('function');
    expect(typeof goPrev).toBe('function');
    expect(typeof goNext).toBe('function');

    act(() => {
      ref.current?.goTo(2);
    });
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(2);
    act(() => {
      ref.current?.goPrev();
    });
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(1);
    act(() => {
      ref.current?.goNext();
    });
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(2);
  });

  it('should have start and stop function and work normally', async () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    const { start, stop } = ref.current || {};

    expect(typeof start).toBe('function');
    expect(typeof stop).toBe('function');

    expect(ref.current?.activeIndex).toBe(0);

    // wait for animation to be finished
    await waitFakeTimer();
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(1);

    ref.current?.stop();
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(1);

    ref.current?.start();
    expect(ref.current?.activeIndex).toBe(1);

    // wait for animation to be finished
    await waitFakeTimer();
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(2);
  });

  it('should trigger onWindowResized after window resize', async () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const spy = jest.spyOn(ref.current?.inner, 'onWindowResized');
    expect(spy).not.toHaveBeenCalled();
    windowResize(1000, window.innerHeight);
    expect(spy).toHaveBeenCalled();
  });

  it('should cancel resize listener when unmount', () => {
    const windowSpy = jest.spyOn(window, 'removeEventListener');
    const { unmount } = render(<Carousel />);

    unmount();
    expect(windowSpy).toHaveBeenCalled();
  });

  it('should active corresponding slider and indicator when the active index changes', async () => {
    const ref = React.createRef<CarouselRef>();

    // autoplay
    const { container, rerender } = render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    expect(container.querySelectorAll('.slider')[0]).toHaveClass(
      'slider-active'
    );
    expect(container.querySelectorAll('.indicator')[0]).toHaveClass(
      'is-active-anim'
    );

    act(() => ref.current?.goTo(1));
    await waitFakeTimer();
    expect(container.querySelectorAll('.slider')[1]).toHaveClass(
      'slider-active'
    );
    expect(container.querySelectorAll('.indicator')[1]).toHaveClass(
      'is-active-anim'
    );

    // not autoplay
    rerender(
      <Carousel autoplay={false} ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    act(() => ref.current?.goTo(0));
    await waitFakeTimer();
    expect(container.querySelectorAll('.slider')[0]).toHaveClass(
      'slider-active'
    );
    expect(container.querySelectorAll('.indicator')[0]).toHaveClass(
      'is-active'
    );

    act(() => ref.current?.goTo(1));
    await waitFakeTimer();
    expect(container.querySelectorAll('.slider')[1]).toHaveClass(
      'slider-active'
    );
    expect(container.querySelectorAll('.indicator')[1]).toHaveClass(
      'is-active'
    );
  });
});
