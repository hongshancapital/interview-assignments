import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../src/App';

test('app basic render', () => {
  render(<App />);
  expect(screen.getByText('xPhone')).toBeInTheDocument();
});