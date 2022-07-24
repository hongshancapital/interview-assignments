import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import App from './App';

const wait = (time: number) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(undefined);
    }, time);
  });
};

test('auto play', async () => {
  const { getByText } = render(<App />);
  const tablet = getByText(/Just the right amount of everything/i).parentElement?.parentElement;
  expect(tablet).toBeInTheDocument();
  expect(tablet).toHaveStyle({transform: 'translate(0%, 0)'});
  await act(async () => {
    await wait(3100);
  });
  expect(tablet).toHaveStyle({transform: 'translate(-100%, 0)'});
});

test('click', async () => {
  const { getByText } = render(<App />);
  const button = getByText(/2/, { selector: '.scdt-carousel-dots div'}).parentElement;
  expect(button).toBeInTheDocument();
  await act(async () => {
    button?.click();
    await wait(100);
  });
  const tablet = getByText(/Just the right amount of everything/i).parentElement?.parentElement;
  expect(tablet).toHaveStyle({transform: 'translate(-200%, 0)'});
});
