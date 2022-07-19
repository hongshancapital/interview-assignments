import React from 'react';
import { render, screen } from '@testing-library/react';

import Carousel from '../index';

beforeEach(() => {
  render(<Carousel />)
})

test('first tab renders width correct text', () => {
  const title = screen.getByText('xPhone');
  expect(title).toBeInTheDocument();

  const txt1 = screen.getByText('Lots to love. Less to spend.');
  expect(txt1).toBeInTheDocument();

  const txt2 = screen.getByText('Starting at $399.');
  expect(txt2).toBeInTheDocument();
})

test('senconde tab renders width correct text', () => {
  const title = screen.getByText('Tablet');
  expect(title).toBeInTheDocument();

  const txt1 = screen.getByText('Just the right amount of everthing.');
  expect(txt1).toBeInTheDocument();
})

test('third tab renders width correct text', () => {
  const txt1 = screen.getByText('Buy a Tablet or xPhone for college.');
  expect(txt1).toBeInTheDocument();

  const txt2 = screen.getByText('Get arPods.');
  expect(txt2).toBeInTheDocument();
})