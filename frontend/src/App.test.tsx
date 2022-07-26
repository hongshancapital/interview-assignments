import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('should take App component snapshot', () => {
  const { asFragment } = render(<App />);

  expect(asFragment()).toMatchSnapshot();
});
