import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('should has all images', () => {
  const { container } = render(<App />);
  const images = container.querySelectorAll('img');

  expect(images.length).toEqual(3);
});
