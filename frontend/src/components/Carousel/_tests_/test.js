import '@testing-library/jest-dom';

import * as React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import CarouselDemo from './demo';

test('carousel should be render', () => {
  render(<CarouselDemo />);

  expect(screen.getByText('xPhone')).toBeInTheDocument();
});
