import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('App render in the document ', () => {
  const { getByTestId } = render(<App />);
  const linkElement = getByTestId("test-app-id"); 
  expect(linkElement).toBeInTheDocument();
});
