import React from 'react';
import { cleanup, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import Carousel from './Carousel';
import xphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";
import airpod from "../assets/airpods.png";

describe('Carousel Tests', () => {
    const data = [
        {
            title: ['xPhone'],
            subTitle: ['Lots to love.Less to speed.', 'Starting at $399'],
            backgroundImage: xphone,
            backgroundColor: '#111111',
            color: '#fff',
        },
        {
            title: ['Tablet'],
            subTitle: ['Just the right amount of everything'],
            backgroundImage: tablet,
            backgroundColor: '#fafafa',
        },
        {
            title: ['Buy a tablet or xPhone for college.', 'Get arPods'],
            subTitle: [],
            backgroundImage: airpod,
            backgroundColor: '#f1f1f3',
        },
    ];

    afterEach(() => {
        cleanup();
    })

    test('Items render', () => {
        const { container } = render(<Carousel data={data} />);
        const items = container.querySelectorAll('.carousel-item');
        expect(items.length).toBe(data.length);
    });

    test('Carousel props', () => {
        const { container } = render(<Carousel data={data} autoPlayTime={3000} easing={'linear'} defaultCurrent={2} />);
        const items = container.querySelectorAll('.carousel-dot-item-animation');
        expect(items[2]).toHaveStyle({
            width: '50px',
            'transition-duration': '3000ms',
            'transition-timing-function': 'linear'
        });
    });

    test('Carousel click event', () => {
        const { container } = render(<Carousel data={data} autoPlayTime={3000} easing={'linear'} defaultCurrent={2} />);
        const items = container.querySelectorAll('.carousel-dot-item');
        const animationItems = container.querySelectorAll('.carousel-dot-item-animation');
        userEvent.click(items[1]);
        expect(animationItems[1]).toHaveStyle({
            width: '50px',
            'transition-duration': '3000ms',
            'transition-timing-function': 'linear'
        });
    });
});
