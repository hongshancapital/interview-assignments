import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import { Carousel, CarouselItem } from './carousel/Carousel';

const mockData: Array<CarouselItem> = [
  {
    title: 'Title 1'
  },
  {
    title: 'Title 2'
  }
];

test('renders carousel component', () => {
  const { container } = render(<App />);
  const carousel = container.querySelector('.carousel-container');
  expect(carousel).toBeInTheDocument();
});

test('renders carousel pages correctly', () => {
  const { container } = render(<App />);
  const carouselItems = container.querySelectorAll('.carousel-item');
  const indicators = container.querySelectorAll('.page-indicator .indicator');
  expect(carouselItems.length).toEqual(indicators.length);
  expect(carouselItems.length).toBeGreaterThan(0);
});

test('renders carousel with mock data', () => {
  const { getByText, container } = render(<Carousel items={mockData} />);
  expect(getByText(/Title 1/i)).toBeInTheDocument();
  expect(getByText(/Title 2/i)).toBeInTheDocument();

  const carouselItems = container.querySelectorAll('.carousel-item');
  expect(carouselItems.length).toEqual(2);
});
