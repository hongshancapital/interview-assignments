import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('App test', () => {
  test('App could be updated and unmounted without errors', () => {
    const { unmount, rerender } = render(<App />);
    expect(() => {
      rerender(<App />);
      unmount();
    }).not.toThrow();
  });

  test('should have App class', () => {
    const { container } = render(<App />);
    const rootElement = container.querySelector('.App');
    expect(rootElement).toBeInTheDocument();
  });
});
