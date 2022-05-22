import React, { useEffect, useMemo, useRef, useState } from "react";
import { carouselItem } from "../CarouselModel";
import "./Slide.scss";
import SlidePage from "./SlidePage";

interface IProps {
    carouselItems: carouselItem[];
    curIndex: number;
}

// slide places all slides in a line and can scroll through index to move correspond slide into slide window.
const Slide:React.FC<IProps> = ({carouselItems, curIndex}) => {
    const [slides, ] = useState<carouselItem[]>(() => carouselItems);
    const stageRef = useRef<HTMLDivElement>(null);

    // Move current slide into slide window.
    function moveSlide() {
        if(stageRef.current) {
            stageRef.current.style.transform = `translateX(${curIndex * -100}vw)`;
        }
    }

    useEffect(() => {
        moveSlide();
    }, [curIndex]);

    useEffect(() => {
        if(stageRef.current) {
            stageRef.current.style.width = `${slides.length * 100}vw`;
        }
    }, []);

    return (
        <div className="slide_collect" ref={stageRef}>
            {
                slides.map((slide, index) => {
                    return (
                        <SlidePage slide={slide} key={index}></SlidePage>
                    )
                })
            }
        </div>
    )
}

export default Slide;