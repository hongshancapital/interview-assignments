import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('render App container', () => {
  const { getByTestId } = render(<App />);
  const appContainer = getByTestId('app');
  expect(appContainer).toBeInTheDocument();
});
