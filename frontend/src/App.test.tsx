import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('render App successfully', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/Just the right amount of everything./i);
  expect(linkElement).toBeInTheDocument();
});
