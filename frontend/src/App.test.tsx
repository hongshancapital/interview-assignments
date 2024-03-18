import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { baseElement } = render(<App />);
  expect(baseElement).toBeInTheDocument();
});
