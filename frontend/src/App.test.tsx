/** @format */

import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
}

test('renders learn react link', () => {
  window.ResizeObserver = ResizeObserver;
  const { getByText } = render(<App />);
  const linkElement = getByText('xPhone');
  expect(linkElement).toBeInTheDocument();
});
