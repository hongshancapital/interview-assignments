import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './';

test('renders images if received as children', () => {
  const { getAllByAltText } = render(
    <Carousel>
      <img src="test1.jpg" alt="test1"/>
      <img src="test2.jpg" alt="test2"/>
      <img src="test3.jpg" alt="test3"/>
    </Carousel>
  );
  const imgElements = getAllByAltText(/test/i);
  expect(imgElements.length).toBe(3);
});

test('should display corresponding number of carousel nav', () => {
  const { container } = render(
    <Carousel>
      <img src="test1.jpg" alt="test1"/>
      <img src="test2.jpg" alt="test2"/>
    </Carousel>
  );
  const navElements = container.getElementsByClassName('carousel__nav')
  expect(navElements.length).toBe(2);
});

test('should wrap children inside carousel__container', () => {
  const { getAllByAltText } = render(
    <Carousel>
      <img src="test1.jpg" alt="test1"/>
      <img src="test2.jpg" alt="test2"/>
    </Carousel>
  );
  const imgElements = getAllByAltText(/test/i);
  expect(imgElements[0].parentElement?.className).toBe('carousel__container');
});
