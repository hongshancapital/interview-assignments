import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';

test('Carousel', () => {
  const { container } = render(<Carousel></Carousel>);
  const img = container.querySelector('.img');
  expect(img).toBeInstanceOf(HTMLElement);
});
