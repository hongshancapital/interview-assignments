import { ReactNode } from "react";

/**
 * CarouselProps
 */
export interface CarouselProps {
    /**
     * 组件内容
     */
    children: ReactNode;
    /**
     * 切换页码时的回调
     * @param current number 
     * @returns 
     */
    onChangeCurrent?: (current: number) => void;
    /**
     * 是否开启自动轮播
     * @desc 值为数字时是轮播间隔时间 单位是 ms
     * @default false
     */
    autoplay?: number | boolean;
    /**
     * 切花轮播时的过渡动画时长 单位是 ms
     * @default 300
     */
    speed?: number;
    /**
     * 是否显示左右切换按钮
     * @default true
     */
    showSwitch?: boolean;
    /**
     * 是否显示导航指示点
     * @default true
     */
    dots?: boolean;
    /**
     * 宿主css类名
     */
    className?: string;
}
