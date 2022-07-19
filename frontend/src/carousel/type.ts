import React from "react";

enum CarouseCodes {
    duration = 3000,
    speed = 300,
}

export interface CarouseProps {
    // 幻灯片停留时间
    duration?: number;
    // 滑动速度
    speed?: number;
    // 是否显示面板指示点
    dots?: boolean;
    // 是否自动切换
    autoplay?: boolean;
    className?: string;
    style?: React.CSSProperties;
    children?: React.ReactNode;
}

export interface CarouselItemProps {
    className?: string;
    style?: React.CSSProperties;
    children?: React.ReactNode;
}

export interface DotsProps {
    // 幻灯片停留时间
    duration?: number;
    // 数量
    count?: number;
    // 当前播放幻灯片
    currIndex?: number;
    className?: string;
    goTo?: Function
}

export default CarouseCodes