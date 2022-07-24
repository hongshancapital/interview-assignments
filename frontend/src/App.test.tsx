import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import App from './App';
import Carousel from "./Carousel";

test('renders App', () => {
  render(<App />);
  const result = screen.getAllByText(/xPhone/i);
  expect(result.length).toBe(2)
});


test('test Carousel component', () => {
  render(<Carousel>
    <div className="carousel-item carousel-item-first">
      <h1>xPhone</h1>
      <h2>Lots to love. Less to spend.</h2>
      <h3>Starts at $399.</h3>
    </div>
    <div className="carousel-item carousel-item-second">
      <h1>Tablet</h1>
      <h2>Just the right amount of everything.</h2>
    </div>
    <div className="carousel-item carousel-item-third">
      <h1>Buy a Tablet or xPhone for college.</h1>
      <h1>Get airPods.</h1>
    </div>
  </Carousel>);
  const result = screen.getAllByText(/xPhone/i);
  expect(result.length).toBe(2)
  expect(screen.getByText(/airPods/)).toBeDefined()

  fireEvent.click(screen.getAllByTestId('indicator')[0]);
  expect(screen.getAllByTestId('indicator')[0].childNodes).toContain(document.querySelector('.indicator-item-progress'));
});
