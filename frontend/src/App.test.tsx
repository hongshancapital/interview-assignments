import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('has 1 carousel container', () => {
  const dom = render(<App />);
  const carousel = dom.container.getElementsByClassName('carousel-container')
  expect(carousel.length).toBe(1)
});

