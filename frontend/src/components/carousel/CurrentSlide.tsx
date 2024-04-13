import './slide.css';
import { ReactNode, useState } from "react";

interface CurrentSlideProps {
    children: ReactNode;
    hasLastSlide: boolean;
    isFirstSlide: boolean;
    time: number;
}

export const CurrentSlide = ({ children, hasLastSlide, isFirstSlide, time }: CurrentSlideProps) => {
    if (!hasLastSlide) {
        return (<div className="current-slide">{children}</div>);
    }
    return (<div className="current-slide" style={{
        animationName: isFirstSlide ? 'fly-from-left' : 'fly-from-right',
        animationIterationCount: 'infinite',
        animationDuration: `${time / 1000}s`
    }}>{children}</div>);
};
