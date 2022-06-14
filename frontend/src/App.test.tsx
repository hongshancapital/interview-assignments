import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('carousel-container can mounted', () => {
  render(<App />);
  expect(screen.queryByText('.carousel-container')).toBeNull()
});

test('content can mounted', () => {
   render(<App />);
   expect(screen.queryByText('.content-item')).toBeNull();
});