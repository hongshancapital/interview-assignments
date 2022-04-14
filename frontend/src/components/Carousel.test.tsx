import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom'
import Carousel from './Carousel';

beforeEach(() => {
  jest.useFakeTimers()
})

test('Carousel component input 3 children and Render', () => {
  const { getByText } = render (
    <Carousel
      duration={5000}
      delay={1000}
    >
      <p>123</p>
      <p>456</p>
      <p>789</p>
    </Carousel>
  );
  expect(getByText('123')).toBeInTheDocument();
  expect(getByText('456')).toBeInTheDocument();
  expect(getByText('789')).toBeInTheDocument();
});

test('Carousel component input 1 children and Render', () => {
  const { container, getByText } = render (
    <Carousel>
      <span>123</span>
    </Carousel>
  );
  expect(getByText('123')).toBeInTheDocument();
  expect(container).toContainHTML('<span>123</span>');
});
