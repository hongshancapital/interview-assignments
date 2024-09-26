import { CarouselContentRender } from './carouselContentRender';
import { CarouselStepProps, CarouselStep } from './carouselStep';
import { ImageProps } from './common/carouselImage';
import { Noop, useInterval } from './hook/useInterval';
import './index.css';
import React, {  ForwardRefRenderFunction, ForwardedRef, ReactElement, ReactNode, forwardRef, useEffect, useImperativeHandle, useRef, useState } from "react";

export interface BaseComponentProps {
    className?: string,
    style?: React.CSSProperties,
    children?: ReactElement,
}
/**
 * 跑马灯内容类型
 */
export type CarouselType = 'Image' | 'Content'; 
export type CarousePageConfigProps = {
    type: CarouselType,
    props?: ImageProps,
    children?: ReactNode,
}[];

export interface CarouselProps extends BaseComponentProps {
    /** 跑马灯配置 */
    pages: CarousePageConfigProps,
    /** 是否自动播放 */
    autoplay?: number | false,
    /** 切换前回调钩子 */
    afterChange?: (currentNumber: number) => void,
    /** 切换后回调钩子 */
    beforeChange?: (currentNumber: number) => void,
}

/** 
 * class 组合器
 */
export const classNames = (...args: (string | undefined)[]) => {
    return args.reduce((preClass, curentClass,) => {
        return `${preClass} ${curentClass || ''}`
    }, '')
}

/**
 * 跑马灯 ref api
 */
export interface CarouseApi {
    /** 
     * 下一页
     */
    next: Noop,
    /**
     * 上一页
     */
    prev: Noop,
    /**
     * 跳转到指定页
     * @param index 
     * @returns 
     */
    goTo: (index: number) => void,

    /**
     * 自动播放
     * @param { number | boolean } playTime boolean： 开启/暂停， number: 控制时长自动播放
     */
    autoPlay: (playTime: number | boolean) => void,
}

/**
 * 跑马灯
 * @param props {@link CarouselProps} 配置工具 
 * @param ref {@link CarouseApi} 控制工具
 * @example
 * ```
 * const carouselRef = useRef<CarouseApi>();
 * const carouselProps: CarouselProps = {...};
 * <Carousel ref={carouselRef} {...carouselProps}/>
 * ```
 */
const Carousel: ForwardRefRenderFunction<CarouseApi, CarouselProps> = ({ 
    pages,
    className,
    autoplay = 2,
    style
}, ref: ForwardedRef<CarouseApi>) => {
    const [renderIndex, setRenderIndex] = useState<number>(0);
    const [renderMs, setRenderMs] = useState<number>(autoplay || 2);
    const pageNumberRef = useRef<number>(pages.length);

    // 循环回调更新当前页索引
    const intervalCallback = () => {
        setRenderIndex((oldRenderNumber) => {
            return (oldRenderNumber + 1) % pageNumberRef.current;
        });
    }

    // 定时器 hook 辅助切换页签
    const [ChangeInrerVal, intervalTool] = useInterval(intervalCallback, renderMs * 1000, !!autoplay);
    
    const { isStopInterval, startInterval, stopInterval, resetInterval } = intervalTool;

    // 配置默认页
    // 重置定时器
    useEffect(function(){   
        setRenderIndex(0);
        pageNumberRef.current = pages.length;
        if (!isStopInterval) {
            resetInterval();
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pages]);

    useImperativeHandle(ref, () => {
        
        return {
            autoPlay: (showTime: number | boolean) => {

                // 暂停 | 开始 自动播放
                if (typeof(showTime) === 'boolean') {
                    if (!showTime) {
                        stopInterval();
                    } else {
                        startInterval();
                    }
                    return;
                }

                // 开始自动播放
                // 播放时长相同只做开启处理
                if (showTime === renderMs) {
                    return startInterval();
                }

                // 设置时长并重置定时器
                if (showTime > 0) {
                    setRenderMs(showTime);
                    ChangeInrerVal({ms: showTime * 1000});
                    startInterval();
                }
            },
            goTo: (index: number) => {

                if (index === renderIndex) {
                    return;
                }

                if (index >= 0 && index <= pageNumberRef.current) {
                    setRenderIndex(index);
                }
            },
            next: () => {
                
                if (!isStopInterval) {
                    resetInterval();
                }
                setRenderIndex((renderIndex + 1) % (pageNumberRef.current));
            },
            prev: () => {
                
                if (!isStopInterval) {
                    resetInterval();
                }
                setRenderIndex((renderIndex - 1) % (pageNumberRef.current));
            }
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [renderIndex, renderMs, isStopInterval]);

    /**
     * 指定播放页
     * @param renderIndex 
     */
    const onChangeRenderIndex = (renderIndex: number) => {
        
        setRenderIndex(renderIndex);

        // 重置定时器
        if (!isStopInterval) {
            resetInterval();
        }
    }

    const carouselStepProps: CarouselStepProps = {
        onChangeRenderIndex,
        renderIndex, 
        pages ,
        renderMs: isStopInterval ? 0 : renderMs,
    };

    return <div className={classNames('carousel', className)} style={style}>

        {/* 播放页 */}
        <CarouselContentRender renderIndex={renderIndex} pages={pages}/>

        {/* 页条 */}
        <CarouselStep  {...carouselStepProps}/>
    </div>
}

export default forwardRef(Carousel);