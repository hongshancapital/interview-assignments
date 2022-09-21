import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders product list', () => {
  const { getByText } = render(<App />);
  const xPhone = getByText(/Lots to love. Less to spend./);
  expect(xPhone).toBeInTheDocument();
  const tablet = getByText(/Just the right amount of everything./);
  expect(tablet).toBeInTheDocument();
  const arPods = getByText(/Buy a Tablet or xPhone for college./);
  expect(arPods).toBeInTheDocument();
});
