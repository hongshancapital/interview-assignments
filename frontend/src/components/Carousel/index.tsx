import React, {FC, useEffect, useRef, useState} from "react";
import useTrack from './hooks/useTrack';
import useMergedState from "./hooks/useMergedState";
import CarouselDots from "./CarouselDots";
import {SPEED} from "../utils";
import './style.scss';

interface ICarouselProps {
    /**
     * 是否开启自动播放
     */
    autoplay?: boolean;
    /**
     * 轮播发生变化回调
     * @param index
     */
    onChange?: (index: number) => void;
    /**
     * 受控情况下，指定激活索引
     */
    activeIndex?: number;
    /**
     * 默认索引
     */
    defaultIndex?: number;
    /**
     * 轮播速度
     */
    speed?: number;
}

const Carousel: FC<ICarouselProps> = ({autoplay=true, children, activeIndex,defaultIndex, onChange, speed= SPEED}) =>  {
    const len = React.Children.count(children);
    const timeRef = useRef(-1);
    const [currentIndex, setCurrentIndex] = useMergedState<number>( 0, {
        defaultValue: defaultIndex,
        value: activeIndex
    });
    const [playing, setPlaying] = useState(false);
    const {listRef, winWidth} = useTrack()
    useEffect(()=>{
        autoplay && play();
        // eslint-disable-next-line
    }, [])

    function play() {
        timeRef.current = window.setTimeout(()=>{
            setCurrentIndex((index) => {
                const value = (index + 1 ) % (len);
                debugger;
                onChange?.(value);
                return  value
            })
            autoplay && play();
        }, speed)
        setPlaying(true);
    }

    function handleClick(index: number) {
        clearTimeout(timeRef.current);
        setCurrentIndex(index);
        autoplay && play();
    }
    return (
        <div className="sequoia-carousel-container">
            <div className="sequoia-carousel-list" ref={listRef}>
                <div className="sequoia-carousel-track" style={{width: len * winWidth, transform: `translate3d(-${winWidth * currentIndex}px, 0px, 0px)`}}>
                    {React.Children.map(children, (elem, index) => {
                        return (
                            <div key={`carousel-item-${index}`} style={{ width: winWidth }} className={`sequoia-carousel-item ${currentIndex === index ? 'active-sequoia-carousel-item': ''}`}>
                                {elem}
                            </div>
                        )
                    })}
                </div>
            </div>
            <CarouselDots count={len} activeIndex={currentIndex} playing={playing} onItemClick={handleClick} speed={speed} />
        </div>
    )
}

export default Carousel;