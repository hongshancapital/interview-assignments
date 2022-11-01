
import React, { FC, useEffect, useState } from "react";
import './index.css'
// import CarouselItem from "./Content";

interface CarouselItemProps {
    title: string;
    subTitle?: string;
    image: string;
    className: string;
}
interface CarouselProps {
    // 停留时间/下方滑动条的滑动时间
    duration?: number;
    // 滑动速度
    speed?: number;
    // 滑动页内容
    items: CarouselItemProps[];
}

// 轮播图内容
const CarouselItem: FC<CarouselItemProps> = ({
    title,
    subTitle,
    image,
    className
}) => {
    return (
        <div className={`swipe-item ${className}`}>
            <div className="item-content">
                <h1>{title}</h1>
                {subTitle && <div className="sub-title">{subTitle}</div>}
            </div>
            <div className="img-box">
                <img src={image} alt="显示图" />
            </div>
        </div>
    );
};


const Carousel: FC<CarouselProps> = ({
    duration = 3000,
    speed = 300,
    items,
}) => {
    console.log(duration, speed, items);
    // 轮播图长度
    const len = items.length;
    // 当前项
    const [currIndex, setCurrIndex] = useState(0);
    // 定时
    const useInterval = (callback: any, delay: number) => {
        useEffect(() => {
            if (delay > 0 ) {
                let timer = setInterval(callback, delay);
                return () => clearInterval(timer);
            }
            return () => {}
        }, [callback, delay])
    }
    useInterval(() => {
        setCurrIndex((prev) => (prev + 1) % len);
    }, duration);
    
    return (
        <div className="swipe-content">
            <div className="swipe-list" style={{ width: 100 * len + '%', transform: `translateX(-${100 * currIndex}vw)`, transition: `all ${speed}ms`}}>
                {
                    items.map((item: CarouselItemProps, index: number) => {
                        return <CarouselItem key={index} {...item} />
                    })
                }
            </div>
            <ul className="dot-list">
                {
                    items.map((item: CarouselItemProps, index: number) => {
                        return <li key={index} className='dot-item'>
                            <span className={currIndex === index ? 'active' : ''} style={{animationDuration: `${duration}ms`}}></span>
                        </li>
                    })
                }
            </ul>
        </div>
    )
}
export default Carousel;