import React from 'react';
import {render} from '@testing-library/react';
import App from './App';

const sleep = (time: number) => new Promise((resolve) => {
  setTimeout(() => {
    resolve(null)
  }, time)
})

test('test carousel step one', () => {
  const {getByText} = render(<App/>);
  const element = getByText(/Lots to love.Less to spend./i);
  expect(element).toBeInTheDocument();
});

test('test carousel step two', async () => {
  const {getByText} = render(<App/>);
  await sleep(4000)
  const element = getByText(/Just the right amount of everything./i);
  expect(element).toBeInTheDocument();
});
