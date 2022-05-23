import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

it('可以正常卸载Carousel组件', () => {
  const { unmount } = render(<App />);
  expect(() => {
    unmount();
  }).not.toThrow();
});
