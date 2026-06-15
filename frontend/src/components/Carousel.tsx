import React, { useEffect, useRef, useState } from "react";
import { CarouselProps } from './type'
import { SingleImageInfo } from '../type'

import './index.css';

function Carousel(props: CarouselProps) {
    let [activeIndex, setActiveIndex] = useState<number>(-1);
    let timer = useRef<NodeJS.Timeout | undefined>();
    const { sliderList, duration }: CarouselProps = props;

    const startIntervalTime = () => {
        timer.current = setInterval(() => {
            setActiveIndex(index => index > 1 ? 0 : index + 1)
        }, duration);
    }

    useEffect(() => {
        setActiveIndex(index => index + 1)
        startIntervalTime();
        return () => clearInterval(timer.current);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const imageItems = () => {
        return sliderList.map((item: SingleImageInfo, index: number) => {
            return (
                <li key={index}>
                    <div className="carousel-item-header"  style={{ color: `${item.color}` }}>
                        <div className="carousel-item-title">{item.title}</div>
                        {item.subTitle && <div className="carousel-item-subtitle">{item.subTitle}</div>}
                        {item.desc && <div className="carousel-item-desc"  style={{ width: `${item.descWidth}px` }}>{item.desc}</div>}
                    </div>
                    {/* <span>测试测试</span> */}
                    <img src={item.src} alt={item.desc} />
                </li>
            )
        })
    }

    const indicatorItems = () => {
        return sliderList.map((item: SingleImageInfo, index: number) => {
            return <li key={index} className={activeIndex === index ? 'active' : ''} onClick={() => handleClick(index)}></li>
        })
    }


    const handleClick = (index: number) => {
        setActiveIndex(index)
        clearInterval(timer.current)
        startIntervalTime()
    }

    return (
        <div className="carousel-wrapper">
            <div className="carousel-box">
                <ul className="carousel-list" style={{transform: `translateX(-${activeIndex * 100}%)`}}>
                    {imageItems()}
                </ul>
            </div>

            <ul className="carousel-indicators">
                {indicatorItems()}
            </ul>
        </div>
    );
}

export default Carousel;

