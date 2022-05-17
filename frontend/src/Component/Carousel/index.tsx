/*
 * @Author: shiguang
 * @Date: 2022-05-17 19:07:20
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-17 19:13:48
 * @Description: Carousel 组件
 */
import React, { memo } from 'react';
import CarouselDot from './CarouselDot';
import { useCarouselAnimate } from './hooks';
import './index.scss';

export const CAROUSEL_CONST = {
    /** 滑块停留时间 ms */
    DEFAULT_DURATION: 4000,
    /** 默认选中下标 0 的 slider */
    INIT_INDEX: 0,
    /** 滑块移动时间 ms */
    DEFAULT_SLIDER_TRANSITION_TIME: 500,
} as const;

export interface CarouselGoTo {
    (sliderIndex: number): void;
}

export interface CarouselProps {
    /** children 兼容 react 18 */
    children: React.ReactNode;
    /** 默认 */
    initIndex?: number;
    /** 切换到指定滑块 */
    goTo?: CarouselGoTo;
    /** 切换到下一滑块 */
    next?: () => void;
    /** 每个滑块持续时间 */
    duration?: number;
}

/**
 * 对 slider 包装 便于后期拓展
 * @returns
 */
const wrapperSlider = () => (child: React.ReactNode) =>
    <div className="comp-carousel-slider">{child}</div>;

/**
 * 计算每次 x 轴移动百分比
 * @param curIndex 当前 slider index
 * @param sliderCount slider count
 * @returns
 */
const computedTransformXPercent = (curIndex: number, sliderCount: number) =>
    (100 / sliderCount) * curIndex;

const Carousel: React.FC<CarouselProps> = (props) => {
    const { children, initIndex = CAROUSEL_CONST.INIT_INDEX } = props;
    // 停留时间不得小于 滑块时间 防止错乱
    const duration = Math.max(
        props?.duration ?? CAROUSEL_CONST.DEFAULT_DURATION,
        CAROUSEL_CONST.DEFAULT_SLIDER_TRANSITION_TIME
    );
    const sliderCount = React.Children.count(children);

    const { curIndex, goTo } = useCarouselAnimate({
        initIndex,
        sliderCount,
        duration,
    });
    const transformXPercent = computedTransformXPercent(curIndex, sliderCount);
    const carouselTrackStyle = {
        width: `${sliderCount}00%`,
        transform: `translate3d(-${transformXPercent}%, 0px, 0px)`,
        transition: `${CAROUSEL_CONST.DEFAULT_SLIDER_TRANSITION_TIME}ms ease 0s`,
    };

    return (
        <div className="comp-carousel">
            <div className="comp-carousel-content">
                <div className="comp-carousel-track" style={carouselTrackStyle}>
                    {React.Children.map(children, wrapperSlider())}
                </div>
            </div>
            <CarouselDot
                sliderCount={sliderCount}
                curIndex={curIndex}
                goTo={goTo}
                duration={duration}
            />
        </div>
    );
};

export default memo(Carousel);
