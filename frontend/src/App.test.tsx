import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { container } = render(<App />);
  const rootElement = container.querySelector('.App');
  expect(rootElement).toBeInTheDocument();
});
