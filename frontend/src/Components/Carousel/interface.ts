import React from "react";

export interface ICarouselProps {
    children?: React.ReactNode;
    /**
     * @description 是否自动播放
     * @defaultValue true
     */
    autoPlay?: boolean;
    /**
     * @description 幻灯片移动速率
     * @defaultValue 3000
     */
    moveSpeed?: number;
    interval?: number;
    onChange?: (index: number, prevIndex: number) => void;
}