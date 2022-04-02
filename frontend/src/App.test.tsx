import React from 'react';
import { render,screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import App from './App';

test('test text', () => {
  const { getByText } = render(<App />);
  const xPhone = getByText("xPhone");
  expect(xPhone).toBeInTheDocument();
  const tablet = getByText("Tablet");
  expect(tablet).toBeInTheDocument();
  const airPods = getByText("Buy a Tablet");
  expect(airPods).toBeInTheDocument();
});

test('test click', () => {
  let {getByTestId} = render(<App />);
  let action = getByTestId('action-item-1')
  userEvent.click(action)
  expect(action).toHaveClass('carousel-action-item')
})