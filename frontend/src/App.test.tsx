import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('init render', () => {
  const { container } = render(<App />);
  expect(container.querySelector('.carousel')).toHaveClass('carousel');
});
