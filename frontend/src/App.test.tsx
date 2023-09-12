import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('carousel test', () => {
  const { getByText } = render(<App />);
  let subTitleElement = getByText(/Lots to Love/i);
  expect(subTitleElement).toBeInTheDocument();
  subTitleElement = getByText(/Just the right amount/i);
  expect(subTitleElement).toBeInTheDocument();
  subTitleElement = getByText(/Buy a Tablet/i);
  expect(subTitleElement).toBeInTheDocument();
});
