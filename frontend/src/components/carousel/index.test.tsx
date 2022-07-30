import React from 'react';
import { render } from '@testing-library/react';
import Carousel from '.';

test('renders learn react link', () => {
  const { getByText } = render(<Carousel />);
  const linkElement = getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
