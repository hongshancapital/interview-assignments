import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders Carousel Component', () => {
  const { container } = render(<App />);
  const sliderContainer = container.getElementsByClassName('carousel-container')[0];
  const indicatorContainer = container.getElementsByClassName('indicator-container')[0];
  expect(sliderContainer).toBeInTheDocument();
  expect(indicatorContainer).toBeInTheDocument();
});
