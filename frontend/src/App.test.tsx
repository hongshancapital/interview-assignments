import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import Carousel from './components/Carousel';

test('App render Carousel', () => {
  const { container } = render(<App />);
  const carouselDomNode = container.getElementsByClassName('carousel-wrapper')[0];
  expect(carouselDomNode).toBeDefined();
});
