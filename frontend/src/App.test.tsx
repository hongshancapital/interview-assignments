import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('render slide description', () => {
  const { getByText } = render(<App />);
  const slideDescriptionElement = getByText(/everything/i);
  expect(slideDescriptionElement).toBeInTheDocument();
});

test('render carousel slide node count', () => {
  render(<App />);
  const carouselElement = document.querySelector('.carousel');
  expect(carouselElement).toBeInTheDocument();

  const slideElements = document.querySelectorAll('.slide');
  expect(slideElements.length).toEqual(3);
});
