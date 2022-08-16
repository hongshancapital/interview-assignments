import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';
import CarouselItem from '../CarouselItem';
describe("Carousel",()=>{
    test('Carousel render in the document', () => {
      const { getByTestId } = render(<Carousel></Carousel>); 
      const linkElement = getByTestId("test-carousel-id");
      expect(linkElement).toBeInTheDocument();
    });
    test('Carousel has render child CarouselItem', () => {
      const { getByTestId } = render(<Carousel>
          <CarouselItem/>
      </Carousel>); 
      const linkElement = getByTestId("test-carousel-item-id"); 
      expect(linkElement).toBeInTheDocument();
    });

});
