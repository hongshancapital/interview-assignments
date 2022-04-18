import React from 'react';
import { render, act } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks'

import Carousel, { CarouselItem } from './index'
import { Iphone, Tablet, Airpods } from "../BannerItems";
import useCurrIndex from '../../hooks/useCurrIndex'

const items: CarouselItem[] = [
    { id: 1, component: Iphone },
    { id: 2, component: Tablet },
    { id: 3, component: Airpods }
];

describe('Carousel', () => {
    test('Carousel items', () => {
        const { container } = render(<Carousel items={items} />)
        const carouselItems = container.querySelectorAll('.carousel-item')
        expect(carouselItems.length).toBe(3);
    })

    test('Carousel dot', () => {
        const { container } = render(<Carousel items={items} />)
        const carouselItems = container.querySelector('.carousel-dot-container')
        expect(carouselItems).toBeInTheDocument();
    })

    beforeEach(() => {
        jest.useFakeTimers()
    })

    test('Carousel curr index', () => {
        const { result } = renderHook(() => useCurrIndex({ indexMax: items.length, time: 3000 }))
        expect(result.current.index).toBe(0);

        act(() => { jest.runOnlyPendingTimers() });
        expect(result.current.index).toBe(1);

        act(() => { jest.runOnlyPendingTimers() });
        expect(result.current.index).toBe(2);

        act(() => { jest.runOnlyPendingTimers() });
        expect(result.current.index).toBe(0);

        jest.clearAllTimers();
    })
})