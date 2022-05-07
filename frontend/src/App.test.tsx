import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders carousel component', () => {
  const { container } = render(<App />);
  const causel = container.getElementsByClassName('carousel')[0]
  expect(causel).toBeInTheDocument()
});
