import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App main entry', () => {
  const ComponentBody = render(<App />);
  console.log('——————App component test start here——————')
  const carousel1Element = ComponentBody.getByText('xPhone')
  const carousel2Element = ComponentBody.getByText('Tablet')
  expect(carousel1Element).toBeInTheDocument()
  expect(carousel2Element).toBeInTheDocument()
});
