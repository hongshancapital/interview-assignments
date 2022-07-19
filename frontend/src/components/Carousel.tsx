/**
*@description 处理基本轮播
*@param 图片列表，轮播宽度，轮播间隔
*/

import React, { useEffect, useState } from 'react';
import './carousel.css'

interface IProps {
    list: string[];
    itemWidth?: number;
    duration?: number;
}

interface IAnimate {
    ele: HTMLElement;
    target: number;
}

const Carousel = (props: IProps) => {
    const { list = [], duration = 2, itemWidth = 500 } = props;
    const total = list.length;
    let [current, setCurrent] = useState(0);

    useEffect(() => {
        const container = document.querySelector('.img-container') as HTMLElement;
        const target = current === (total - 1) ? 0 : -itemWidth * (current + 1);
        const index = current === (total - 1) ? 0 : current + 1

        const currentDotEle = document.querySelectorAll('.dot-item-inner')?.[current] as HTMLElement;
        currentDotEle.style.width = '100%';
        currentDotEle.style.transition = `width ${duration}s linear`;

        setTimeout(() => {
            currentDotEle.style.width = '0';
            currentDotEle.style.transition = '';
            setCurrent(index);
            animate({
                ele: container,
                target,
            });
        }, duration * 1000)
    }, [current])

    return <div className='container' style={{ width: `${itemWidth}px` }}>
        {/* imgs */}
        <ul className='img-container'>
            {list.map((src, index) => {
                return (
                    <li className='img-item' key={index}>
                        <img src={src} style={{ width: `${itemWidth}px`, height: 'auto' }} alt='' />
                    </li>
                )
            })}
        </ul>
        {/* dots */}
        <ul className='dot-container'>
            {Array(list.length).fill('').map(item => {
                return (
                    <li className='dot-item'>
                        <div className='dot-item-inner' />
                    </li>
                )
            })}
        </ul>
    </div>
}

// 轮播动画方法
function animate({ ele, target }: IAnimate) {
    // @ts-ignore
    ele.timer = setInterval(function () {
        var step = (target - ele.offsetLeft) / 10;
        step = step > 0 ? Math.ceil(step) : Math.floor(step);
        if (ele.offsetLeft === target) {
            // @ts-ignore
            clearInterval(ele.timer);
        }
        ele.style.left = ele.offsetLeft + step + 'px';
    }, 15);
}

export default Carousel;