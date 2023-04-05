import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('render banner content', () => {
  const { container } = render(<App />);
  const banners = container.getElementsByClassName('banner')
  expect(banners.length).toBe(3);
});
