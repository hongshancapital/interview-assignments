/**
 * @jest-environment jsdom
 */
import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import App from './App';

test('renders text', async () => {
  const { getByText, getByTestId, container } = render(<App />);
  const LotsText = getByText(/Lots to love/i);
  const everyThingText = getByText(/amount of everything/i);
  const airPodsText = getByText(/Get airPods./i);
  expect(LotsText).toBeInTheDocument();
  expect(everyThingText).toBeInTheDocument();
  expect(airPodsText).toBeInTheDocument();

  // 点击底部的 index bar
  const itemContainers = container.querySelectorAll('.carousel-bar-item-container');
  await fireEvent.click(itemContainers[1]);
  expect(getByTestId('1')).toBeValid();
  await fireEvent.click(itemContainers[0]);
  expect(getByTestId('0')).toBeValid();
  // throw error
  // expect(getByTestId('2')).toBeValid();
});
