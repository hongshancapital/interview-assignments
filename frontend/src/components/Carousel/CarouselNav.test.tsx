import React from 'react';
import { render } from '@testing-library/react';
import CarouselNav from './CarouselNav';

test('renders carousel nav', () => {
  const { container } = render(
    <CarouselNav active={ false } />
  );
  const nav = container.getElementsByClassName('carousel__nav')
  const activeNav = container.getElementsByClassName('carousel__nav--active')
  expect(nav.length).toBe(1);
  expect(activeNav.length).toBe(0);
});

test('renders active carousel nav if active is true', () => {
  const { container } = render(
    <CarouselNav active={ true } />
  );
  const activeNav = container.getElementsByClassName('carousel__nav--active')
  expect(activeNav.length).toBe(1);
});
