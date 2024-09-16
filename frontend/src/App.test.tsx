import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('App test', () => {

  test('Should render carousel', () => {
    const { container } = render(<App />);
    
    const carousel = container.querySelector('.carousel')
    expect(carousel).toBeInTheDocument()
  });
  
  test('Should render pages', () => {
    const { getByText } = render(<App />);
    const page1 = getByText(/Lots to love. Less to spend/i);
    expect(page1).toBeInTheDocument();
    const page2 = getByText(/Just the right amount of everything./i);
    expect(page2).toBeInTheDocument();
    const page3 = getByText(/Buy a Tablet or xPhone for college./i);
    expect(page3).toBeInTheDocument();
  });
})