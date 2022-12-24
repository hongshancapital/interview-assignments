import React from 'react';
import { render } from '@testing-library/react';
import { CarouselItem } from './App';
import Carousel from './components/carousel';


const imgSrc='https://images.unsplash.com/photo-1451772741724-d20990422508?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'

const items = () => {
  return [
    <CarouselItem src={imgSrc} className='iphone' key={1} titles={['xPhone']} descriptions={['Lots to love.Less to Spend.', 'Starting at $399.']}/>
  ]
}

describe('Carousel test', () => {
  test('img count', () => {
    const { container } = render(<Carousel>{items()}</Carousel>);
    const imgs = container.querySelectorAll('.img-wrap');
    expect(imgs.length).toEqual(2);
  });

  test('active dots', () => {
    const { container } = render(<Carousel>{items()}</Carousel>);
    expect(container.querySelector('.carousel-dots__item--active')).toBeInTheDocument();
  });

});
