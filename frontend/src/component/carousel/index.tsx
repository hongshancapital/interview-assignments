import React, { useCallback, useEffect, useState } from "react";
import { config } from "../../config";
import './index.css';

interface CarouseProps {
    duration: number;
}
type ProgressAnimation = (progressIndex: number) => React.CSSProperties;

export const Carousel: React.FC<CarouseProps> = (props) => {
    const { children, duration, } = props;
    const count = React.Children.count(children);
    const [index, setIndex] = useState(0);

    // 自动轮播
    useEffect(() => {
        const timer = setInterval(() => {
            if (index >= count - 1) {
                setIndex(0);
            } else {
                setIndex(index + 1);
            }
        }, duration);
        return () => clearInterval(timer);
    }, [count, duration, index]);

   const progressAnimation: ProgressAnimation = useCallback((progressIndex: number) => {
    const speed = 1000;
    if (progressIndex === index) {
        return {
            animation: `progress ${duration / speed}s linear`
        };
    }
    return {}
   }, [duration, index]);


    return (
        <div className="carousel">
            <div
                className="slide-wrap"
                style={{
                    transform: `translateX(-${index * global.innerWidth}px)`,
                }}
            >
                {children}
            </div>
            <div className="progress-wrap">
                {config.map((info, progressIndex) => {
                    return (
                        <div key={progressIndex} className="progress">
                            <div
                                className="progress-active"
                                style={progressAnimation(progressIndex)}
                            />
                        </div>
                    )
                })}
            </div>
        </div>
    )
}