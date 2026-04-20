import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { container } = render(<App />);
  const appElement = container.querySelector('.App');
  const carouselElement = container.querySelector('.carousel-container');
  expect(appElement).toBeInTheDocument();
  expect(carouselElement).toBeInTheDocument();
  if (carouselElement?.innerHTML) {
    expect(appElement).toContainHTML(carouselElement.innerHTML);
  }
});
