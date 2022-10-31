import React from 'react';
import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import Carousel from './Carousel';
import { act } from 'react-dom/test-utils';
import useInterval from './useInterval';

const INTERVAL = 100;

beforeEach(() => {
  jest.useFakeTimers();
})

test('render carousel and auto play', () => {
  const renderResult = render(
  <Carousel interval={INTERVAL}>
    <div data-testid="p1"> page1 </div>
    <div data-testid="p2"> page2 </div>
    <div data-testid="p3"> page3 </div>
  </Carousel>);
  const wrapperEl = renderResult.container.querySelector('.carousel__wrapper');
  expect(wrapperEl).toHaveStyle('transform: translate3D(-0%, 0, 0);');
  act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(wrapperEl).toHaveStyle('transform: translate3D(-100%, 0, 0);');

})

test('interval hook pause and resume works', () => {
  const mock = jest.fn();
  const {result} = renderHook(() => useInterval(mock, INTERVAL));

  act(() => {
    // first itv
    jest.runOnlyPendingTimers();
    expect(mock).toBeCalledTimes(1);

    // second itv
    jest.runOnlyPendingTimers();
    expect(mock).toBeCalledTimes(2);

    // wati for a moment
    jest.advanceTimersByTime(50);

    result.current.pause();
    jest.runOnlyPendingTimers();
    expect(result.current.paused).toBe(true);
    expect(mock).toBeCalledTimes(2);

    result.current.resume();
    // continue itv after resume
    jest.advanceTimersByTime(49);
    expect(mock).toBeCalledTimes(2);

    jest.advanceTimersByTime(2);
    expect(mock).toBeCalledTimes(3);
  });
});

test('interval hook stop and resume works', () => {
  const mock = jest.fn();
  const {result} = renderHook(() => useInterval(mock, INTERVAL));

  act(() => {
    // first itv
    jest.runOnlyPendingTimers();
    expect(mock).toBeCalledTimes(1);

    // after 80ms
    jest.advanceTimersByTime(80);
    result.current.stop();
    // will not trigger
    jest.runOnlyPendingTimers();
    expect(mock).toBeCalledTimes(1);

    result.current.resume();
    // restart a new itv
    jest.advanceTimersByTime(21);
    // no trigger
    expect(mock).toBeCalledTimes(1);
    jest.advanceTimersByTime(80);
    // trigger the new itv
    expect(mock).toBeCalledTimes(2);
  })
})