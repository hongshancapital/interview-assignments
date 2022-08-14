import { render } from '@testing-library/react';
import React from 'react';
import App from './App';

test('App render', () => {
  const { container } = render(<App />);
  expect(container.firstChild).toHaveClass('App');
  expect(container).toMatchSnapshot();
})