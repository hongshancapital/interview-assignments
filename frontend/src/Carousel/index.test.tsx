import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index'
import CarouselItem1 from "./item1";
import CarouselItem2 from "./item2";
import CarouselItem3 from "./item3";

test('轮播图添加子元素',()=> {
    const children = []
    children.push(<CarouselItem1 />)
    children.push(<CarouselItem2 />)
    children.push(<CarouselItem3 />)
    
    render(<Carousel carouseItemList={children} duration={2000} delay={3000}/>)
    const caruselFrameList = document.getElementsByClassName("carousel-frame")
    expect(caruselFrameList.length > 0)
    expect(caruselFrameList[0].childNodes.length === 3)
})

