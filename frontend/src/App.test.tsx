import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('App', () => {
  it('component should be render and unmount correctly', () => {
    const { unmount, rerender } = render(<App />);
    expect(() => {
      rerender(<App />);
      unmount();
    }).not.toThrow();
  });
});
