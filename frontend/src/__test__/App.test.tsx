import React from 'react';
import { render } from '@testing-library/react';
import App from '../App';

test('app entry render', () => {
  const { getByTestId } = render(<App />);
  const appContainer = getByTestId('app-entry');
  expect(appContainer).toBeInTheDocument();
});
