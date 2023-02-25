import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders slider', () => {
  const { getByText } = render(<App />);
  const slider = getByText(/1/i);
  expect(slider).toBeInTheDocument();
});
