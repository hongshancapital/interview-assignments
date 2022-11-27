import React from 'react';
import { render, screen } from '@testing-library/react';
import { Carousel } from './index';

describe('carousel', () => {
  test('render carousel', () => {
    const { container } = render((
      <Carousel style={{width: 100, height: 100}} autoPlay>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    ))
    const carouselElement = screen.getByTestId('carousel')
    expect(carouselElement).toBeInTheDocument()
    expect(carouselElement.style.height).toBe('100px')
    expect(carouselElement.style.height).toBe('100px')

    const carouselItemRenderer = screen.getByTestId('carousel-renderer')
    expect(carouselItemRenderer).toBeInTheDocument()
    expect(carouselItemRenderer.childNodes[0].childNodes.length).toBe(3)
    
    
    const carouselIndicator = screen.getByTestId('carousel-indicator')
    expect(carouselIndicator).toBeInTheDocument()
    expect(carouselIndicator.childNodes.length).toBe(3)
  })
})

