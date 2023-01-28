import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders carousel', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText("Lots to love. Less to spend");
  expect(linkElement).toBeInTheDocument();
});
