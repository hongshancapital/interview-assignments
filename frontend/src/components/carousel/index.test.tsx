import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';

test('only one child', () => {
  const { container } = render(<Carousel><div>1</div></Carousel>);
  const dots = container.querySelector('.dots');
  expect(dots).toBeNull();
});
