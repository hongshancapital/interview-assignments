import React from 'react';
import { render, cleanup} from '@testing-library/react';
import {act} from "react-dom/test-utils";
import Carousel from "../index";

afterEach(cleanup);

jest.useFakeTimers();

describe('Carousel', ()=> {
    it('测试渲染个数是否正确', ()=>{
        const carousel = render(<Carousel>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(carousel.container.querySelectorAll('.carousel')).toHaveLength(3);
        expect(carousel.container.querySelectorAll('.active')).toHaveLength(1);
    })
    it('autoplay有一个是被激活状态的', ()=>{
        const loadData = jest.fn();
        const carousel = render(<Carousel onChange={loadData}>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(carousel.container.querySelectorAll('.active')).toHaveLength(1);
    })
    it('onchange被调用', ()=>{
        const loadData = jest.fn();
        render(<Carousel onChange={loadData}>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(loadData).not.toBeCalled();
        act(() => {
            jest.runOnlyPendingTimers();
        });
        expect(loadData).toBeCalled();
        jest.clearAllTimers();
    })
    it('测试指定defaultIndex', ()=>{
        const carousel = render(<Carousel autoplay={false} defaultIndex={1}>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(carousel.container.querySelectorAll('.sequoia-carousel-item')[1]).toHaveClass('active-sequoia-carousel-item');
    })
    it("测试受控", ()=>{
        let carousel = render(<Carousel autoplay={false} activeIndex={1}>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(carousel.container.querySelectorAll('.sequoia-carousel-item')[1]).toHaveClass('active-sequoia-carousel-item');
        carousel.rerender(<Carousel autoplay={false} activeIndex={2}>
            <div className="carousel"></div>
            <div className="carousel"></div>
            <div className="carousel"></div>
        </Carousel>);
        expect(carousel.container.querySelectorAll('.sequoia-carousel-item')[2]).toHaveClass('active-sequoia-carousel-item');
    })
});