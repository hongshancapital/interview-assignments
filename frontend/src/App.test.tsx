import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App', () => {
  const { baseElement } = render(<App />);
  const linkElement = baseElement.getElementsByClassName('App')[0]
  expect(linkElement).toBeInTheDocument();
});
