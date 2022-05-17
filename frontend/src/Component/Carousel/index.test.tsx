/*
 * @Author: shiguang
 * @Date: 2022-05-17 19:22:30
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-17 20:03:00
 * @Description: Carousel 组件测试
 */

import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import Carousel from '.';

describe('carousel component test', () => {

    it('carousel in page', () => {
        const { getByText } = render(
            <Carousel>
                <h2>slider 1</h2>
                <h2>slider 2</h2>
                <h2>slider 3</h2>
            </Carousel>
        );
        expect(document.querySelector('.comp-carousel-track')).toBeInTheDocument();
        expect(document.querySelector('.comp-carousel-track')?.children.length).toBe(3);

        expect(getByText('slider 1')).toBeInTheDocument();
        expect(getByText('slider 1')?.parentElement?.parentElement?.style.width).toBe('300%');

        const compCarouselDotEl = document.querySelector('.comp-carousel-dot');
        expect(compCarouselDotEl).toBeInTheDocument();

        const compCarouselDotItemEl = document.querySelector('.comp-carousel-dot-item');
        expect(compCarouselDotItemEl).toBeInTheDocument();


        expect(compCarouselDotEl?.children.length).toBe(3);
    })

    it('carousel slider play', () => {
        jest.useFakeTimers();
        const spySetTimeout = jest.spyOn(global, 'setTimeout');
        const { container } = render(
            <Carousel>
                <h2>slider 1</h2>
                <h2>slider 2</h2>
                <h2>slider 3</h2>
            </Carousel>
        );

        expect(spySetTimeout).toHaveBeenCalled();
        expect(spySetTimeout).toBeCalledTimes(1);

        const getPercent = (count: number, idx: number) => 100 / count * idx;
        const getPercentByCount3 = getPercent.bind(null, 3);
        const transformStyleList = Array.from({ length: 3 }).map((_, index) => `transform: translate3d(-${getPercentByCount3(index)}%, 0px, 0px)`);
        const compCarouselDotItemList = container.getElementsByClassName('comp-carousel-dot-item');


        const compCarouselTrackDom = container.getElementsByClassName('comp-carousel-track')[0];
        expect(compCarouselTrackDom).toHaveStyle(transformStyleList[0]);


        fireEvent.click(compCarouselDotItemList[2]);
        expect(compCarouselDotItemList[2]).toHaveClass('comp-carousel-dot-item-active');
        expect(compCarouselTrackDom).toHaveStyle(transformStyleList[2]);
        expect(spySetTimeout).toBeCalledTimes(2);

        fireEvent.click(compCarouselDotItemList[1]);
        expect(compCarouselDotItemList[1]).toHaveClass('comp-carousel-dot-item-active');
        expect(compCarouselTrackDom).toHaveStyle(transformStyleList[1]);
        expect(spySetTimeout).toBeCalledTimes(3);

        fireEvent.click(compCarouselDotItemList[0]);
        expect(compCarouselDotItemList[0]).toHaveClass('comp-carousel-dot-item-active');
        expect(compCarouselTrackDom).toHaveStyle(transformStyleList[0]);
        expect(spySetTimeout).toBeCalledTimes(4);

    });

});
