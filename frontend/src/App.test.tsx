import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import App from './App';

it('test carousel page', () => {
  jest.useFakeTimers();
  const { queryByTestId } = render(<App />);
  const box = queryByTestId('carousel-box');
  const indicator1 = queryByTestId('indicator-0');
  const indicator2 = queryByTestId('indicator-1');
  const indicator3 = queryByTestId('indicator-2');

  expect(box).toHaveStyle('left: 0vw');
  expect(indicator1).toHaveStyle('visibility: visible');

  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(box).toHaveStyle('left: -100vw');
  expect(indicator2).toHaveStyle('visibility: visible');

  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(box).toHaveStyle('left: -200vw');
  expect(indicator3).toHaveStyle('visibility: visible');

  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(box).toHaveStyle('left: 0vw');
  expect(indicator1).toHaveStyle('visibility: visible');
});