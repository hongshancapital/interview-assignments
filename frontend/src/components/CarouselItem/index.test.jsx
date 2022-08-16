import React from 'react';
import { render } from '@testing-library/react';
import CarouselItem from './index';
describe("CarouselItem",()=>{
    test('CarouselItem render in the document', () => {
      const { getByTestId } = render(<CarouselItem></CarouselItem>);
      const linkElement = getByTestId("test-carousel-item-id");
      expect(linkElement).toBeInTheDocument();
    });

});
