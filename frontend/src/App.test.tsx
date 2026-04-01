import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders Tablet', () => {
  const { getByText } = render(<App />);

  console.log('getByText=',getByText)
  const linkElement = getByText('xPhone');
  expect(linkElement).toBeInTheDocument();
});
