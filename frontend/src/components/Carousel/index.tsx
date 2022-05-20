import React, { FC } from "react";
import classNames from "classnames";

import CarouselItem, { ICarouselItemProps } from "./CarouselItem";
import CarouselDot from "./CarouselDot";
import { useCurrentIndex } from "../../hooks/useCurrentIndex";

import './index.scss';

export interface ICarouselProps {
    // 轮播项数据
    items: ICarouselItemProps[],
    // 轮播图停留时长，默认3000ms
    duration?: number;
}

const Carousel: FC<ICarouselProps> = (props) => {
    const {items, duration = 3000} = props;
    const { currentIndex } = useCurrentIndex({
        total: items.length,
        duration: duration
    })
    const commonCls = 'carousel';

    return (
        <div className={classNames(commonCls)}>
            <div 
                className={classNames(`${commonCls}-items-container`, 
                `${commonCls}-items-container-slide-${currentIndex}`)}>
                {
                    items.map((item, index) => (
                        <CarouselItem
                            key={`item-${index}`}
                            title={item.title}
                            desc={item.desc}
                            img={item.img}
                            bgColor={item.bgColor}
                            titleColor={item.titleColor}>
                        </CarouselItem>
                    ))
                }
            </div>
            <div className={classNames(`${commonCls}-dots-container`)}>
                {
                    items.map((item, index) => (
                        <CarouselDot
                            key={`dot-${index}`}
                            isActive={currentIndex === index} 
                            duration={duration}>
                        </CarouselDot>
                    ))
                }
            </div>
        </div>
    )
}

export default Carousel;