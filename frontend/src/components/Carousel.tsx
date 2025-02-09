import React, { useEffect, useRef, useState } from "react";
import "./index.css";
import { IImage } from "./interface";

interface IProps {
    lists: IImage[]
}

function Carousel(props: IProps) {
    let [activeIndex, setActiveIndex] = useState<number>(-1);
    let timer = useRef<NodeJS.Timeout | undefined>()

    useEffect(() => {
        setActiveIndex(index => index + 1)
        startInterval();
        return () => clearInterval(timer.current);
    }, [])

    const imageItems = () => {
        const { lists } = props
        return lists.map((item: IImage, index: number) => {
            return (
                <li key={index}>
                    <img src={item.src} alt={item.name}/>
                </li>
            )
        })
    }

    const indicatorItems = () => {
        const { lists } = props
        return lists.map((item: IImage, index: number) => {
            return <li key={index} className={activeIndex === index ? 'active' : ''} onClick={() => handleClick(index)}></li>
        })
    }

    /**
     * 设置定时器
     */
    const startInterval = () => {
        timer.current = setInterval(() => {
            setActiveIndex(index => index > 1 ? 0 : index + 1)
        }, 3000);
    }

    /**
     * 鼠标点击切换
     * @param index
     */
    const handleClick = (index: number) => {
        setActiveIndex(index)
        clearInterval(timer.current)
        startInterval()
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
