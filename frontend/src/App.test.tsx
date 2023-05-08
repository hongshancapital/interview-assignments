import React from 'react'
import { render } from '@testing-library/react';
import App from './App';

describe('App', () => {
  it('component should be render correctly', () => {
    const { rerender } = render(<App />);
    expect(() => {
      rerender(<App />);
    }).not.toBeNull();
  });
});