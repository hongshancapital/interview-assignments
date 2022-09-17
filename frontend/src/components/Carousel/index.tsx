import React, { useEffect, useState } from "react";
import "./index.css";

export interface CarouselProps {
    /**是否自动播放, 数字表示每一帧持续时间 ms */
    autoplay?: false | number;
    /**子元素 */
    children?: React.ReactNode;
}
export default function Index({ autoplay = 1000, ...props }: CarouselProps) {
    const children: React.ReactNode = props.children;
    const childCount: number = React.Children.count(children);

    const [currentIndex, setCurrentIndex] = useState<number>(0); // 当前显示的索引
    let timer: NodeJS.Timeout | undefined = undefined; // 自动播放定时器
    // 切换上一张
    const prev = () => {
        setCurrentIndex((old) => {
            if (old === 0) return childCount - 1;
            return old + 1;
        });
    };

    // 切换下一张
    const next = () => {
        setCurrentIndex((old) => {
            console.log(old);
            if (old === childCount - 1) return 0;
            return old + 1;
        });
    };

    // 跳到指定张
    const goto = (index: number) => {
        setCurrentIndex(index);
    };

    useEffect(() => {
        if (autoplay) {
            autoPlay();
        } else {
            stopAutoPlay();
        }
        return stopAutoPlay;
    }, [autoplay]);

    // 循环播放
    const autoPlay = () => {
        if (typeof autoplay === "number") timer = setInterval(next, autoplay);
    };

    // 关闭自动播放
    const stopAutoPlay = () => {
        // 清除定时器
        if (timer) clearInterval(timer);
    };

    return (
        <div className="carousel-container">
            <ul
                className="main-box"
                style={{
                    transition: currentIndex === childCount ? "0s" : "0.5s",
                    width: `${childCount * 100}%`,
                    left: `-${100 * currentIndex}%`
                }}
            >
                {React.Children.map(children, (child, i) => (
                    <li key={i} className="carousel-item">
                        {child}
                    </li>
                ))}
            </ul>
            <div className="indicator-box">
                {React.Children.map(children, (child, i) => (
                    <div
                        className="indicator-item"
                        key={i}
                        onClick={() => goto(i)}
                    >
                        <div
                            className={`indicator-item-inner ${
                                currentIndex === i ? "active" : ""
                            }`}
                            style={{
                                animationDuration: `${(autoplay || 0) / 1000}s`
                            }}
                        ></div>
                    </div>
                ))}
            </div>
        </div>
    );
}
