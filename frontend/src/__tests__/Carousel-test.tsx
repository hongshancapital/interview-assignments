

import React from 'react';
import CarouselTest from "../components/Carousel/CarouselTest";
import { render } from '@testing-library/react';
 
describe('CarouselTest', () => {
  test('renders CarouselTest component', () => {
    render(<CarouselTest />);
  });
});