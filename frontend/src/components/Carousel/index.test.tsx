import React from 'react';
import { render } from '@testing-library/react';
import {Carousel} from './index';

test('renders learn react link', () => {
  const { getByText } = render(<Carousel />);
  expect(getByText(/Carousel/i)).toBeInTheDocument()
});
