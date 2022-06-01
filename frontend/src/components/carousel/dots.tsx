import React, { FC } from "react";
import { CarouselConfigEnum, DotsProps } from "./type";

const Dots: FC<DotsProps> = ({
    className = "",
    progress = true, // 非自动播放时禁用进度条
    activeIndex = 0,
    interval = CarouselConfigEnum.interval,
    count = 0,
    switchTo
}) => (
    <ul className={`carousel-dots ${className}`}>{
        Array.from(Array(count).keys()).map((index) =>
            <li key={index} onClick={() => { switchTo?.(index) }}>
                {progress && <div
                    className={activeIndex === index ? "carousel-dots-progress" : ""}
                    style={{ animationDuration: `${interval}ms` }}
                />}
            </li>
        )
    }</ul>);

export default Dots;
