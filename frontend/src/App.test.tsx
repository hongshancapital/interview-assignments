import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('test', () => {
  it('test Carousel item 1', () => {
    const { getByText } = render(<App />);
    
    expect(getByText('xPhone')).toBeInTheDocument()

    expect(getByText('Tablet')).toBeInTheDocument()

    expect(getByText('Buy a Tablet or xPhone for college.')).toBeInTheDocument()

    expect(getByText('Get airPods.')).toBeInTheDocument()
  })
})