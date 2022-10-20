import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('test renders APP', () => {
  // test app should be defined
  const app = render(<App />);
  expect(app).toBeDefined();
});
