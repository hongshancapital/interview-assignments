import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App', () => {
  const { getByText, queryAllByRole } = render(<App />);
  const textElement = getByText(/Lots to love/i);
  expect(textElement).toBeInTheDocument();

  const imgElements = queryAllByRole('img')

  expect(imgElements).toHaveLength(3)
  expect(imgElements[0]).toHaveClass('item-image')
});

