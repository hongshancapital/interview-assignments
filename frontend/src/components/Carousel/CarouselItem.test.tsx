import React from 'react';
import { render } from '@testing-library/react';
import CarouselItem from './CarouselItem';

test('renders carousel item with given background color', () => {
  const { container } = render(
    <CarouselItem bgColor={ '#fff' }>
      <span>test1</span>
    </CarouselItem>
  );
  expect(container.firstChild).toHaveStyle({ backgroundColor: 'rgb(255, 255, 255)' });
});

test('renders carousel item with given background color', () => {
  const { getAllByText } = render(
    <CarouselItem bgColor={ '#fff' }>
      <span>test1</span>
      <span>test2</span>
    </CarouselItem>
  );
  const children = getAllByText(/test/)
  expect(children.length).toBe(2);
  expect(children[0]).toBeInTheDocument();
  expect(children[1]).toBeInTheDocument();
});