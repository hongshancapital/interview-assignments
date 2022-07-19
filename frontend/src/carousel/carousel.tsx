import React, { FC, useEffect, useRef, useState } from "react";
import CarouseCodes, { CarouseProps } from "./type";
import Dots from "./dots";
import "./index.css";

const Carousel: FC<CarouseProps> = ({
        duration = CarouseCodes.duration,
        speed = CarouseCodes.speed,
        dots = true,
        autoplay = true,
        ...props
    }) => {
    const prevCount = useRef(React.Children.count(props.children));
    // 子元素更新
    useEffect(() => {
        if (prevCount.current !== React.Children.count(props.children)) {
            prevCount.current = React.Children.count(props.children);
        }
    }, [props.children]);

    // 定时器
    const [currIndex, setCurrIndex] = useState(0);

    useCarouseInterval(
        () => {
            if (autoplay) {
                setCurrIndex((prev) => (prev + 1) % prevCount.current);
            }
        },
        autoplay && duration ? duration : -1
    );

    // 换页
    const goTo = (index: number) => {
        setCurrIndex(index);
    };

    const style = {
        width: 100 * prevCount.current + "%",
        transform: `translateX(-${100 * currIndex}vw)`,
        transition: `all ${speed}ms`,
    };
    return (
        <div className={`slick-slider ${props.className ? props.className : ""}`}>
            <div className="slick-list" style={style}>
                {props.children}
            </div>
            {dots && prevCount.current && (
                <Dots
                    count={prevCount.current}
                    currIndex={currIndex}
                    goTo={goTo}
                ></Dots>
            )}
        </div>
    );
};

// 自定义定时器hook
const useCarouseInterval = (callBack: Function, delay: number) => {
    return useEffect(() => {
        if (delay) {
            let timer = setInterval(callBack, delay);
            return () => clearInterval(timer);
        }
    }, [callBack, delay]);
};

export default Carousel;