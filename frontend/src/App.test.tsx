import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { container } = render(<App />);
  expect(container.querySelectorAll('.Carousel')).toHaveLength(1);
  screen.debug();
});
