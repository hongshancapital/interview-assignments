import React, { FC, useCallback } from "react";
import { CarouselItem } from "../Carousel";
import './index.css'

interface CarouselDotProps {
    index: number;
    time: number;
    items: CarouselItem[];
}

const CarouselDot: FC<CarouselDotProps> = (props) => {
    const { index: currIndex, time, items } = props;

    // 活动动画
    const dotItemActionAnimation = useCallback((index: number) => {
        if (index !== currIndex) return null;
        return (
            <div 
                className="carousel-dot-animate" 
                style={{ animationDuration: `${time/1000}s` }} 
            />
        );
    }, [currIndex, time]);

    // 渲染项
    const renderDotItem = useCallback((item: CarouselItem, index: number) => {
        return (
            <div className="carousel-dot-item" key={item.id}>
                {dotItemActionAnimation(index)}
            </div>
        )
    }, [dotItemActionAnimation]);

    return (
        <div className="carousel-dot-container">
            <div className="carousel-dot-wrapper">
                {items.map(renderDotItem)}
            </div>
        </div>
    )
}

export default CarouselDot;
