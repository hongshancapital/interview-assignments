import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App', () => {
  const { container } = render(<App />)
  expect(container.querySelector('.App')).toBeInTheDocument()
  expect(container.querySelectorAll('.App').length).toBe(1)
})
