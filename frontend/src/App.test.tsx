import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { getByText, getByTestId } = render(<App />)
  const result = getByTestId('app')
  expect(result).toBeInTheDocument()
});
