/*
 * @Author: shiguang
 * @Date: 2022-05-17 19:07:34
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-17 19:14:20
 * @Description: CarouselDot 组件
 */
import React, { CSSProperties, memo } from 'react';
import { CarouselGoTo } from '.';

export interface CarouselDotProps {
    /** slider 数量 */
    sliderCount: number;
    /** 当前 index */
    curIndex: number;
    /** 跳转到 */
    goTo: CarouselGoTo;
    /** 持续时间 */
    duration: number;
}

export interface CreateDotItemOptions extends Pick<CarouselDotProps, 'sliderCount' | 'curIndex'>{
    /** dot 点击事件 */
    defaultClick: (clickIndex: number) => void;
    style: CSSProperties;
}

/**
 * 通过配置创建 dotItems
 * @param options 创建 dotItems 需要的配置
 * @returns 
 */
const createItemsDom = (options: CreateDotItemOptions) => {
    const { defaultClick, curIndex, sliderCount: length, style } = options;
    return Array.from({ length }, (_, index) => (
        <div
            key={index}
            className={`comp-carousel-dot-item ${curIndex === index ? 'comp-carousel-dot-item-active' : ''}`}
            onClick={defaultClick.bind(null, index)}
        >
            <div className="comp-carousel-dot-item-progress" style={style} />
        </div>
    ));
};

const CarouselDot: React.FC<CarouselDotProps> = ({ curIndex, sliderCount, goTo, duration }) => {
    const dotItemClick = goTo;
    const itemsDom = createItemsDom({ 
        defaultClick: dotItemClick, 
        curIndex, 
        sliderCount,
        style: {
            animationDuration: `${duration}ms`
        }
    });

    return (
        <div className="comp-carousel-dot"> 
            {itemsDom}
        </div>
    );
};

export default memo(CarouselDot);
