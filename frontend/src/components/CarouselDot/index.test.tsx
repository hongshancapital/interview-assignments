import React from 'react';
import { render } from '@testing-library/react';

import CarouselDot from './index'
import { CarouselItem } from '../Carousel'
import { Iphone, Tablet, Airpods } from "../BannerItems";
const items: CarouselItem[] = [
    { id: 1, component: Iphone },
    { id: 2, component: Tablet },
    { id: 3, component: Airpods }
  ];;


describe('CarouselDot', () => {
    test('CarouselDot items', () => {
        const { container } = render(<CarouselDot index={1} time={3000} items={items} />)
        const carouselDotItems = container.querySelectorAll('.carousel-dot-item')
        expect(carouselDotItems.length).toBe(3);
    })

    test('CarouselDot action animation index 0', () => {
        const index = 0;
        const { container } = render(<CarouselDot index={index} time={3000} items={items} />)
        const carouselDotItems = container.querySelectorAll('.carousel-dot-item')
        const activeAnimation = carouselDotItems[index].querySelector('.carousel-dot-animate')
        expect(activeAnimation).toBeInTheDocument();
    })

    test('CarouselDot action animation index 1', () => {
        const index = 1;
        const { container } = render(<CarouselDot index={index} time={3000} items={items} />)
        const carouselDotItems = container.querySelectorAll('.carousel-dot-item')
        const activeAnimation = carouselDotItems[index].querySelector('.carousel-dot-animate')
        expect(activeAnimation).toBeInTheDocument();
    })

    test('CarouselDot action animation index 2', () => {
        const index = 2;
        const { container } = render(<CarouselDot index={index} time={3000} items={items} />)
        const carouselDotItems = container.querySelectorAll('.carousel-dot-item')
        const activeAnimation = carouselDotItems[index].querySelector('.carousel-dot-animate')
        expect(activeAnimation).toBeInTheDocument();
    })
})