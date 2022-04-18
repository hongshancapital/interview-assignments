import React from 'react';
import {render, screen, fireEvent, cleanup} from '@testing-library/react';
import CarouselDots from "../CarouselDots";

afterEach(cleanup);

describe('CarouselDots', ()=> {
    it('测试渲染的个数是否正确', ()=>{
        const dots = render(<CarouselDots count={4} />);
        expect(dots.container.querySelectorAll('.sequoia-carousel-dot-item')).toHaveLength(4);
    })
    it("测试点击切换", async () => {
        const loadData = jest.fn();
        render(<CarouselDots count={4} onItemClick={loadData} />);
        fireEvent.click(screen.getAllByRole('dot')[2])
        expect(loadData).toHaveBeenCalledWith(2);
    })
    it("判断激活的节点是否正确", ()=>{
        const dots = render(<CarouselDots count={4} activeIndex={1} playing={true} />);
        expect(dots.container.querySelectorAll('.sequoia-carousel-dot')[1]).toHaveClass('active')
    })
    it("判断修改speed正确", ()=> {
        const dots = render(<CarouselDots count={4} activeIndex={1} playing={true} speed={5000} />);
        expect(dots.container.querySelectorAll('.sequoia-carousel-dot')[1]).toHaveStyle({transitionDuration: '5000ms'});
        expect(dots.container.querySelectorAll('.sequoia-carousel-dot')[2]).toHaveStyle({transitionDuration: 'unset'});
    })
});