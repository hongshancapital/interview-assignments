import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders 3 carouse indicator', () => {
  render(<App />);
  expect(document.querySelectorAll('.carouselIndicatorItem').length).toBe(3);
});
