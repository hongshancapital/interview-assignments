import React from 'react';
import { render, act } from '@testing-library/react';
import Carousel, { CarouselRef } from '.';

/**
 * Wait for a time delay. Will wait `advanceTime * times` ms.
 *
 * @param advanceTime Default 1000
 * @param times Default 20
 */
export async function waitFakeTimer(advanceTime = 1000, times = 20) {
  for (let i = 0; i < times; i += 1) {
    // eslint-disable-next-line no-await-in-loop
    await act(async () => {
      await Promise.resolve();

      if (advanceTime > 0) {
        jest.advanceTimersByTime(advanceTime);
      } else {
        jest.runAllTimers();
      }
    });
  }
}

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should has prev, next and goTo function', async () => {
    const ref = React.createRef<CarouselRef>();

    render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );

    const { prev, next, goTo } = ref.current || {};
    expect(typeof prev).toBe('function');
    expect(typeof next).toBe('function');
    expect(typeof goTo).toBe('function');
    ref.current?.goTo(2);
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(2);
    ref.current?.goTo(1);
    await waitFakeTimer();
    expect(ref.current?.activeIndex).toBe(1);
  });
});