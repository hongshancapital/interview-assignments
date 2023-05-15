import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders XPhone', () => {
  const { getByText } = render(<App />);
  const element = getByText('XPhone');
  expect(element).toBeInTheDocument();
});
