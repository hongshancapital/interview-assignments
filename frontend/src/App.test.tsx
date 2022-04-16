import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test("render app", () => {
  const { container } = render(<App />);
  const appElem = container.querySelector(".App");
  const carouselElem = container.querySelector(".carousel-wrap");
  expect(carouselElem).not.toBeNull();
  expect(appElem).toContainHTML(carouselElem!.innerHTML);
});
