import React from 'react';
import {render, screen} from '@testing-library/react';
import App from './App';

test('app渲染', () => {
  const app = render(<App />);

  expect(app.container.querySelector('.sequoia-carousel-container')).toBeInTheDocument();
});
