/*
 * @Author: 'xing.feng' '243279223@qq.com'
 * @Date: 2022-07-10 14:04:02
 * @LastEditors: 'xing.feng' '243279223@qq.com'
 * @LastEditTime: 2022-07-10 20:12:00
 * @FilePath: \frontend\src\components\Carousel.tsx
 * @Description: carousel组件
 * 
 * Copyright (c) 2022 by 'xing.feng' '243279223@qq.com', All Rights Reserved. 
 */
import React, { useState, useEffect, useRef, useCallback } from "react";
import './Carousel.css';

interface ItemProps {
    title?: Array<string>, // Item的标题
    subTitle?: Array<string>, // Item的副标题
    backgroundImage?: string, // Item的背景图片
    backgroundColor?: string, // Item的背景色
    color?: string, // Item的文本色
}

interface CarouselProps {
    data: ItemProps[], // 展示的数据
    autoPlayTime?: number, // 自动播放的时间周期
    animationTime?: number, // 切换动画的时间周期
    infinity?: Boolean, // 是否循环播放
    easing?: string, // 动画效果
    defaultCurrent?: number, // 默认展示的Item的索引
}

function Carousel({
    data = [],
    autoPlayTime = 3000,
    animationTime = 500,
    infinity = true,
    easing = 'ease',
    defaultCurrent = 0,
}: CarouselProps) {
    const [width, setWidth] = useState<number>(0); // 容器宽度
    const [current, setCurrent] = useState<number>(NaN); // 当前索引
    const containerRef = useRef<HTMLDivElement>(null); // 获取容器DOM
    let timer = useRef<NodeJS.Timer>(); // 计时器，自动切换
    
    /**
     * @description: 自动切换到下一个Item
     * @return {*}
     */    
     const next = useCallback(() => {
        if (current >= data.length - 1) {
            setCurrent(0);
        } else {
            setCurrent(current + 1);
        }
    }, [current, data]);

    useEffect(() => {
        setWidth(getWidth());
        setCurrent(defaultCurrent);
        window.addEventListener('resize', () => {
            setWidth(getWidth());
        });
    }, [defaultCurrent]);

    useEffect(() => {
        if (timer.current) {
            clearInterval(timer.current);
        }
        if (autoPlayTime && infinity) {
            timer.current = setInterval(() => {
                next();
            }, autoPlayTime)
        }

        return () => {
            clearInterval(timer.current);
        }
    }, [current, autoPlayTime, infinity, next]);

    /**
     * @description: 获取容器的width
     * @return {*}
     */
    const getWidth = () => {
        return containerRef.current?.offsetWidth || 0;
    };

    /**
     * @description: 切换到指定的索引
     * @param {number} index: 指定的索引
     * @return {*}
     */    
    const goto = (index: number) => {
        setCurrent(index);
    };

    return (
        <div className="carousel-container" ref={containerRef}>
            <div className="carousel-track" style={{ width: width * data.length, transform: `translate3d(-${current * width}px, 0, 0)`, transitionDuration: `${animationTime}ms`, transitionTimingFunction: `${easing}` }}>
                {
                    data.map((item: ItemProps, index) => (
                        <div className="carousel-item" style={{ width, backgroundColor: item.backgroundColor, color: item.color }} key={index}>
                            {
                                item.title?.map((title: String, ind) => (
                                    <h1 key={ind}>{title}</h1>
                                ))
                            }
                            {
                                item.subTitle?.map((title: String, ind) => (
                                    <h3 key={ind}>{title}</h3>
                                ))
                            }
                            <img className="carousel-img" src={item.backgroundImage} height="100" alt="显示图片" />
                        </div>
                    ))
                }
            </div>
            <div className="carousel-dot">
                {
                    data.map((item: ItemProps, index) => (
                        <div className="carousel-dot-item" key={index} onClick={() => goto(index)}>
                            <div className="carousel-dot-item-animation" style={{ width: current === index ? '50px' : 0, transitionDuration: current === index ? `${autoPlayTime}ms` : '0s', transitionTimingFunction: `${easing}` }}></div>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default Carousel;