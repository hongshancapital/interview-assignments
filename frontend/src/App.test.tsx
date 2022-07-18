import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders text', () => {
  const { getByText } = render(<App />);
  expect(getByText(/Just the right amount of everything/i)).toBeInTheDocument();
});
