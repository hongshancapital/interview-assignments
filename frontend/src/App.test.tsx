import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('能够渲染出界面', () => {
  const { getByText } = render(<App />);
  const el = getByText(/Lots to love. Less to spend./i);
  expect(el).toBeInTheDocument()
});
