import { render } from '@testing-library/react';
import React from 'react';
import Carousel from './index';

describe('carousel component', () => {
    it('dots', () => {
        let carouselDom = render(<Carousel dots><div key={1}>1</div><div key={2}>2</div><div key={3}>3</div></Carousel>);
        expect(carouselDom.container.getElementsByClassName('custom-carousel-dots-item').length).toEqual(3);
    })
    it('no dots', () => {
        let carouselDom = render(<Carousel dots={false}><div key={1}>1</div><div key={2}>2</div><div key={3}>3</div></Carousel>);
        expect(carouselDom.container.getElementsByClassName('custom-carousel-dots-item').length).toEqual(0);
    })
})
