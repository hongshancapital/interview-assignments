import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import { Carousel } from './Carousel';

jest.mock('./Carousel');
const mockedCarousel = jest.mocked(Carousel);

test('renders learn react link', () => {
  render(<App />);
  expect(mockedCarousel).toHaveBeenCalled();
});
