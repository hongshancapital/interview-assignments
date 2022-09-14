import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('成功渲染 Carousel 组件', () => {
  const { container } = render(<App />);
  expect(container.querySelector('.carousel')).toBeTruthy();
});
