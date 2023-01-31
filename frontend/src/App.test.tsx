import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('render carousel', () => {
  const { container } = render(<App />);
  const CarouselContainer = container.querySelector('.carousel-container')
  expect(CarouselContainer).toBeInTheDocument();
});
test('render carousel-item', () => {
  const { container } = render(<App />);
  const CarouselContainer = container.querySelector('.carousel-container');
  expect(CarouselContainer?.querySelectorAll(".carousel-item").length).toBe(3)
});
