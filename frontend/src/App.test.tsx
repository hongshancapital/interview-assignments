import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('should render success', () => {
  const { getByText } = render(<App />);
  const firstElement = getByText(/Lots to love. less to spend/i);
  const secondElement = getByText(/Just the right amount of everything/i);
  const thirdElement = getByText(/Buy a Tablet or xPhone for college/i);
  expect(firstElement).toBeInTheDocument();
  expect(secondElement).toBeInTheDocument();
  expect(thirdElement).toBeInTheDocument();
});
