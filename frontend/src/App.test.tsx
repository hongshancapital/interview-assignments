import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('Renders App', () => {
  const { container } = render(<App />);

  const AppContainer = container.querySelector('.App')
  const CarouselContainer = container.querySelector('.carousel-container')

  expect(AppContainer).toBeInTheDocument();
  expect(CarouselContainer).toBeInTheDocument();
});
