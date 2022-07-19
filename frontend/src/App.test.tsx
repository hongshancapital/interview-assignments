import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders Carousel', () => {
  const { getByTestId } = render(<App />);
  console.log(getByTestId("Carousel"))
  expect(getByTestId("Carousel")).not.toBeNull();
});
