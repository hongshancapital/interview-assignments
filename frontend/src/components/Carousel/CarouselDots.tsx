import React, { ReactNode } from "react";
import { SPEED } from "../utils";
import './style.scss';

interface ICarouselDotsProps {
    /**
     * 渲染锚点的个数
     */
    count: number;
    /**
     * 激活锚点的索引
     */
    activeIndex?: number;
    /**
     * 是否播放状态
     */
    playing?: boolean;
    /**
     * 锚点点击事件
     * @param index
     */
    onItemClick?: (index: number) => void;
    /**
     * 锚点动画切换时间，跟轮播时间相同。
     */
    speed?: number;
}

function CarouselDots({ count, activeIndex, playing, onItemClick, speed = SPEED }: ICarouselDotsProps) {
    const dots: ReactNode[] = [];

    function handleClick(index: number) {
        onItemClick?.(index);
    }

    for (let i = 0; i < count; i++) {
        const active = i === activeIndex && playing;
        const className = active ? 'sequoia-carousel-dot active' : 'sequoia-carousel-dot';
        const item = (
            <li role="dot" key={i} className="sequoia-carousel-dot-item" onClick={() => handleClick(i)} >
                <div className={className} style={{ transitionDuration: active ? `${speed}ms` : "unset" }} />
            </li>
        );
        dots.push(item);
    }
    return (
        <ul className="sequoia-carousel-dot-container">
            {dots}
        </ul>
    );
}

export default CarouselDots;