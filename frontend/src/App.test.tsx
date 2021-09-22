import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('first Slider will be cloned to last position', () => {
  render(<App />);
  
  expect(screen.getAllByText(/\$399/)).toHaveLength(2);
});
