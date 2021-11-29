import React from 'react';
import { render, within, fireEvent } from '@testing-library/react';

import { Carousel } from './index';

describe('test carousel component',() => {
    const items = [
        {
            id: 'iphone',
            title: ['xPhone']
        },
        {
            id: 'tablet',
            title: ['Tablet']
        }
    ];
    
    test('check carousel items', () => {
        const { container } = render(<Carousel items={items} />);
        const carouselItems = container.querySelectorAll('.carousel-item-container');
        expect(carouselItems.length).toBe(2);

        const firstChildTitle = carouselItems[0].querySelector('.title') as HTMLDivElement;
        within(firstChildTitle).getByText('xPhone');

        const secondChildTitle = carouselItems[1].querySelector('.title') as HTMLDivElement;
        within(secondChildTitle).getByText('Tablet');
    });

    test('click second indicator item', () => {
        const { container } = render(<Carousel items={items} />);
        const indicatorItems = container.querySelectorAll('.indicator-item');
        expect(indicatorItems.length).toBe(2);

        const secondIndicatorItem = indicatorItems[1];
        const secondIndicatorSlider = secondIndicatorItem.querySelector('.slider');
        fireEvent.click(secondIndicatorItem);
        expect(secondIndicatorSlider?.getAttribute('class')).toContain('active');        
    });
});