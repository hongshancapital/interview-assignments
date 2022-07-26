import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('App', () => {
  it('should render carousel component', () => {
    const { getByTestId } = render(<App />);

    expect(getByTestId('carousel-component')).toBeTruthy();
  });
});