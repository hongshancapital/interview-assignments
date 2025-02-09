import React from 'react';
import { act, render } from '@testing-library/react';
import { Carousel } from '.';
import list from './mock-list';

test('Carousel Item Dot Count', () => {
  const { getAllByRole } = render(<Carousel list={list} />);
  const renderItem = getAllByRole('button');
  expect(renderItem.length).toBe(3);
});

test('Carousel Item Dot Click', () => {
  const { getAllByRole } = render(<Carousel list={list} />);
  const renderItem = getAllByRole('button');
  act(() => renderItem[1].click());
  expect(renderItem[1].parentElement).toHaveClass('active');
});
