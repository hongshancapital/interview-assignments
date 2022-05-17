import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe(`mount and unmount`, () => {
  it(`component could be updated and unmounted without errors`, () => {
    const { unmount, rerender } = render(<App />);
    expect(() => {
      rerender(<App />);
      unmount();
    }).not.toThrow();
  });
});
