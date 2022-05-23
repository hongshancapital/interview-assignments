/*
 * @Author: shiguang
 * @Date: 2022-05-17 19:07:59
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-23 23:40:08
 * @Description: 自定义 hooks
 */
import React from 'react';
import { useCallback, useEffect, useLayoutEffect, useRef, useState } from 'react';
import { CAROUSEL_CONST } from '.';

/**
 * 缓存函数 解决闭包陷阱的同时不引起引用变化
 * @param handler
 * @returns
 */
export const useEvent = <F extends (...params: any[]) => any>(handler: F) => {
    const handlerRef = useRef<F>();

    // 视图渲染完成后更新`handlerRef.current`指向
    useLayoutEffect(() => {
        handlerRef.current = handler;
    });

    // 用useCallback包裹，使得render时返回的函数引用一致
    return useCallback((...args: Parameters<F>): ReturnType<F> | undefined => {
        return handlerRef.current?.(...args);
    }, []);
};

export interface UseCarouselAnimateOptions {
    /** 初始 slider 下标 */
    initIndex: number;
    /** slider 数量  */
    sliderCount: number;
    /** 持续时间  */
    duration: number;
}

/**
 * carousel 动画相关状态核心逻辑。包括 slider 滚动记时，展示状态，跳转操作等
 * @param options 配置
 * @returns res res.goTo 跳转, res.curIndex 当前页面, res.next 下一页
 */
export const useCarouselAnimate = (options: UseCarouselAnimateOptions) => {
    const { initIndex, sliderCount, duration } = options;
    const [curIndex, setCurIndex] = useState(initIndex);
    const timeoutIdRef = useRef<NodeJS.Timeout>();

    /**
     * 跳转到某一个 slider
     * @param curIndex 对应 index
     */
    const goTo = useEvent((_curIndex: number) => {
        setCurIndex(_curIndex >= sliderCount ? 0 : _curIndex);
    });

    /**
     * 跳转下一个
     * @returns
     */
    const next = useEvent(goTo.bind(null, curIndex + 1));

    useEffect(() => {
        /**
         * 计算持续时间 通过 setTimeout 模拟 setInterval, 防止意外内存泄露, 细粒度控制
         * @returns
         */
        const _clearTimeout = () => (timeoutIdRef.current && window.clearTimeout(timeoutIdRef.current));
        timeoutIdRef.current = setTimeout(next, duration);
        return _clearTimeout;
    }, [curIndex, duration, next]);

    return {
        goTo,
        curIndex,
        next,
    };
};


/**
 * 计算每次 x 轴移动百分比
 * @param curIndex 当前 slider index
 * @param sliderCount slider count
 * @returns
 */
const computedTransformXPercent = (curIndex: number, sliderCount: number) =>
    (100 / sliderCount) * curIndex;

const CAROUSEL_TRANSLATE_CLASS_NAME_PREFIX = 'J_comp-carousel-track-translate3d';
const CAROUSEL_TRACK_CLASS_NAME_PREFIX = 'J_comp-carousel-track';

/**
* 动态将样式渲染成 class
* @param options.sliderCount slider count
* @param options.curIndex 当前 slider index
* @returns 
*/
export const useCarouselStyleToClass = (sliderCount: number, curIndex: number) => {
    const [classKey, setClassKey] = useState<string>('');
    useEffect(() => {
        setClassKey(`${Math.random()}-${performance.now()}`.split('.').join(''));
    }, [sliderCount]);

    const translate3dClass = Array.from({ length: sliderCount }).map((_, index) => {
        const _transformXPercent = computedTransformXPercent(index, sliderCount);
        return (
            `
             .${CAROUSEL_TRANSLATE_CLASS_NAME_PREFIX}-${classKey}-${index}{
                 transform: translate3d(-${_transformXPercent}%, 0px, 0px);
             }
             
         `
        );
    });
    const carouselTrackClassName = `${CAROUSEL_TRACK_CLASS_NAME_PREFIX}-${classKey}`;

    return {
        curClassName: `${CAROUSEL_TRANSLATE_CLASS_NAME_PREFIX}-${classKey}-${curIndex} ${carouselTrackClassName}`,
        styleEl: (
            <style>
                {
                    `
                     .${carouselTrackClassName}{
                         width: ${sliderCount}00%;
                         transition: ${CAROUSEL_CONST.DEFAULT_SLIDER_TRANSITION_TIME}ms ease 0s;
                     }
                 `
                }
                {translate3dClass}
            </style>
        )
    };
};