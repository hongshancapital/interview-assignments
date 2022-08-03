import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders carousel', () => {
  const { getByText } = render(<App />);
  const carouselElement = getByText(/Lots to love\.Less to spend/i);
  expect(carouselElement).toBeInTheDocument();
});
