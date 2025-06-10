import React, { useEffect, useState, useRef } from "react";
import Dots  from '../Dots';
import './index.css';

export interface CarouselProps {
    children: JSX.Element[];
    speed?: number; // 轮播切换速度
    autoplay?: boolean; // 是否自动播放
    delay?: number; // 自动播放间隔
    dots?: boolean; // 是否显示dot
    onChange?: (index: number) => void; // 轮播回调
}

const defaultProps = {
    speed: 0.5,
    autoplay: true,
    delay: 3000,
    dots: true
}

const Carousel: React.FC<CarouselProps> = (_props)=> {
    const ref = useRef<number | null>(null);
    const [scrollIndex, setScrollIndex] = useState<number>(0);

    const props = {...defaultProps, ..._props};

    useEffect(() => {
        if (props.autoplay && props.children.length !== 1) {
            if (ref.current) clearInterval(ref.current);
            ref.current = window.setInterval(() => {
                if (scrollIndex === props.children.length - 1) {
                    setScrollIndex(0);
                } else {
                    setScrollIndex(scrollIndex + 1);
                }
            }, props.delay);
            return () => {
                if (ref.current) clearInterval(ref.current);
            }
        }
    }, [scrollIndex]);

    const scrollHanlder = (index: number) => {
        if (index === scrollIndex) return;
        setScrollIndex(index);
        props.onChange && props.onChange(index);
    }

    return (
        <div className='carousel_box'>
            <div
                className='scroll_box'
                style={{
                    transform: `translateX(-${scrollIndex * 100}%)`,
                    transitionDuration: `${props.speed}s`
                }}
            >
                {
                    props.children.map((item: JSX.Element, index: number) => {
                        return (
                            <div
                                className='scroll_item'
                                style={{ left: `${index * 100}%` }}
                                key={item.key}
                            >
                                {item}
                            </div>
                        )
                    })
                }
            </div>
            {
                props.dots &&
                <Dots
                    autoplay={props.autoplay}
                    delay={props.delay}
                    scrollIndex={scrollIndex}
                    dotNumber={props.children.length}
                    scrollHanlder={scrollHanlder}
                />
            }
        </div>
    )
}

export default Carousel;