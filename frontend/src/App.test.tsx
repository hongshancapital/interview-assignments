import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App', () => {
  const { container } = render(<App />);
  const appDom = container.getElementsByClassName('App')[0];
  expect(appDom).toBeDefined();

  const carouselDom = appDom.getElementsByClassName('carousel-box')[0];
  expect(carouselDom).toBeDefined();
});
