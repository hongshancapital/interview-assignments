import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import App from '../App';

beforeEach(() => {
  jest.useRealTimers();
});

test('renders correctly, make sure there are contents and indicator', () => {
  const { queryByText, queryByTestId } = render(<App />);
  expect(queryByText('xPhone')).toBeTruthy();
  expect(queryByTestId('indicator')).toBeTruthy();
});

test('active index is 0 when initial', () => {
  const { queryByTestId } = render(<App />);
  expect(queryByTestId('current-page-index-0')).toBeTruthy();
});

test('switch to next page', () => {
  const { queryByTestId, getByTestId } = render(<App />);
  expect(queryByTestId('current-page-index-0')).toBeTruthy();
  fireEvent.click(getByTestId('app'));
  expect(queryByTestId('current-page-index-0')).toBeFalsy();
  expect(queryByTestId('current-page-index-1')).toBeTruthy();
  fireEvent.click(getByTestId('app'));
  expect(queryByTestId('current-page-index-2')).toBeTruthy();
  fireEvent.click(getByTestId('app'));
  expect(queryByTestId('current-page-index-0')).toBeTruthy();
});

test('auto switch when animation end', () => {
  const { queryByTestId, getByTestId } = render(<App />);

  // all item exist
  expect(queryByTestId('indicator-item-0')).toBeTruthy();
  expect(queryByTestId('indicator-item-1')).toBeTruthy();
  expect(queryByTestId('indicator-item-2')).toBeTruthy();

  // only one current when initial
  expect(queryByTestId('current-page-index-0')).toBeTruthy();
  expect(queryByTestId('current-page-index-1')).toBeFalsy();
  expect(queryByTestId('current-page-index-2')).toBeFalsy();

  fireEvent.animationEnd(getByTestId('indicator-item-0'));
  expect(queryByTestId('current-page-index-0')).toBeFalsy();
  expect(queryByTestId('current-page-index-1')).toBeTruthy();
  expect(queryByTestId('current-page-index-2')).toBeFalsy();
  expect(getByTestId('indicator-item-1')).toHaveClass('indicator-mask-active');

  fireEvent.animationEnd(getByTestId('indicator-item-1'));
  expect(queryByTestId('current-page-index-0')).toBeFalsy();
  expect(queryByTestId('current-page-index-1')).toBeFalsy();
  expect(queryByTestId('current-page-index-2')).toBeTruthy();
  expect(getByTestId('indicator-item-2')).toHaveClass('indicator-mask-active');

  fireEvent.animationEnd(getByTestId('indicator-item-2'));
  expect(queryByTestId('current-page-index-0')).toBeTruthy();
  expect(queryByTestId('current-page-index-1')).toBeFalsy();
  expect(queryByTestId('current-page-index-2')).toBeFalsy();
  expect(getByTestId('indicator-item-0')).toHaveClass('indicator-mask-active');
});
