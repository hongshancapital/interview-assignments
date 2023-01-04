import React from 'react';
import { render } from '@testing-library/react';
import App, { testData } from './App';

describe('render success', () => {

  it('render app', () => {
    const { container } = render(<App />);
    const appEl = container.querySelector('.App');
    expect(appEl).toBeInTheDocument();
  });

  it('render carousel', () => {
    const { container } = render(<App />);
    const carouselEl = container.querySelector('.carousel-wrapper');
    const cardItemEl = carouselEl?.querySelectorAll('.card');
    expect(carouselEl).toBeInTheDocument();
    expect(cardItemEl?.length).toEqual(testData.data.length);
  });

  it('render dot', () => {
    const { container } = render(<App />);
    const dotWrapperEl = container.querySelector('.dot-wrapper');
    const dotItemEl = dotWrapperEl?.querySelectorAll('.dot-content')
    expect(dotWrapperEl).toBeInTheDocument();
    expect(dotItemEl?.length).toEqual(testData.data.length);
  });
  
});
