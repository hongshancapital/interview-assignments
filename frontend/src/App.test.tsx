import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

jest.mock('./components/carousel', () => () => <div data-testid="carousel" />)

test('the Demo renders the Carousel component', () => {
  const { getByTestId } = render(<App />)

  expect(getByTestId('carousel')).toBeInTheDocument()
})