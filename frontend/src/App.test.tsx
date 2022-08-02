import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders carousel', () => {
  const { getByText } = render(<App />);
  const textInSlide1 = getByText(/Lots to love. Less to spend./i);
  expect(textInSlide1).toBeInTheDocument();

  const textInSlide2 = getByText(/Just the right amount of everything./i);
  expect(textInSlide2).toBeInTheDocument();

  const textInSlide3 = getByText(/Buy a Tablet or xPhone for college./i);
  expect(textInSlide3).toBeInTheDocument();
});
