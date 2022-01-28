import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('app basic render', () => {
  render(<App />);
  const container = document.querySelector('.App');
  const carouselElement = container.children[0]
  expect(carouselElement.className).toBe('carousel');
  expect(carouselElement.children.length).toBe(2);
  const slider = carouselElement.children[0]
  expect(slider.className).toBe('carousel-slider');
  expect(slider.children.length).toBe(3);
  expect(screen.getByText('xPhone')).toBeInTheDocument();
});