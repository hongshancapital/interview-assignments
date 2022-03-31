/**
 * @version 0.0.1
 * @author curryfu
 * @date 2022-03-30
 * @description 完整轮播图UI
 */
import React, { useEffect, useRef, useState } from "react";
import Indicator from "../Indicator/Indicator";
import Slide from "../Slide/Slide";
import "./Carousel.css";

interface CarouselProps {
    indicatorIsShow: boolean;
    delay: number;
    initialIndex: number;
    transitionTime: number;
}

const Carousel: React.FC<Partial<CarouselProps>> = (props: Partial<CarouselProps>) => {
    const { indicatorIsShow = true, delay = 3000, initialIndex = 0, transitionTime = 1000 } = props;
    const [index, setIndex] = useState<number>(initialIndex) // 用于记录当前展示那个轮播图
    let timer = useRef<number>(0);

    useEffect(
        () => {
            timer.current = window.setTimeout(() => {
                setIndex(index + 1 === 3 ? 0 : index + 1)
            }, delay)
            return () => {
                if (timer.current) {
                    window.clearTimeout(timer.current)
                }
            }
        }, [index, delay])
    return <div className="carousel">
        {/* 轮播图 */}
        <Slide
            index={index}
            transitionTime={transitionTime}
        />
        {/* 指示器 */}
        <Indicator
            index={index}
            delay={delay}
            indicatorIsShow={indicatorIsShow}
        />
    </div>
}

export default Carousel;
