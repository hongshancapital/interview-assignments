import React from 'react';
import { render } from '@testing-library/react';
import App, { carouselData } from './App';

describe('App render carousel test', () => {
  test('app test', () => {
    const { container } = render(<App />);
    const appElement = container.querySelector('.App');
    expect(appElement).toBeInTheDocument();
  });

  test('card test', () => {
    const { container } = render(<App />);
    const cardElements = container.querySelectorAll('.carousel-card');
    Array.from(cardElements).forEach((card) => {
      expect(card).toBeInTheDocument();
    });
    expect(cardElements.length).toEqual(carouselData.data.length);
  });

  test('timeline test', () => {
    const { container } = render(<App />);
    const activeElement = container.querySelectorAll('.active');
    expect(activeElement.length).toEqual(1);
    expect(activeElement[0]).toBeInTheDocument();
  });
});
