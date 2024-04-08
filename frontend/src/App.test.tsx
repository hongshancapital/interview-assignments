import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('Test app', () => {
  const { getByTestId } = render(<App />)
  const result = getByTestId('app')
  expect(result).toBeInTheDocument();
});
