import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import App from './App';



it('test custom page', () => {
  jest.useFakeTimers();
  const { queryByTestId } = render(<App />);
  expect(queryByTestId('carousel-box')).toHaveStyle('left: 0vw');
  expect(queryByTestId('indicator-0')).toHaveStyle('visibility: visible');
  expect(queryByTestId('indicator-1')).toHaveStyle('visibility: hidden');
  expect(queryByTestId('indicator-2')).toHaveStyle('visibility: hidden');
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(queryByTestId('carousel-box')).toHaveStyle('left: -100vw');
  expect(queryByTestId('indicator-0')).toHaveStyle('visibility: hidden');
  expect(queryByTestId('indicator-1')).toHaveStyle('visibility: visible');
  expect(queryByTestId('indicator-2')).toHaveStyle('visibility: hidden');
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(queryByTestId('carousel-box')).toHaveStyle('left: -200vw');
  expect(queryByTestId('indicator-0')).toHaveStyle('visibility: hidden');
  expect(queryByTestId('indicator-1')).toHaveStyle('visibility: hidden');
  expect(queryByTestId('indicator-2')).toHaveStyle('visibility: visible');
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(queryByTestId('carousel-box')).toHaveStyle('left: 0vw');
  expect(queryByTestId('indicator-0')).toHaveStyle('visibility: visible');
  expect(queryByTestId('indicator-1')).toHaveStyle('visibility: hidden');
  expect(queryByTestId('indicator-2')).toHaveStyle('visibility: hidden');
});