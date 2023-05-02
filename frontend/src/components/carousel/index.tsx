import React, { FC,ReactNode, useContext, useCallback, useEffect,useLayoutEffect, useRef, useState } from "react";
import { EventType } from './index.d';
import { widthWrapper } from "./hoc/withWrapper";
import { SliderWrapperContent } from './context/sliderWrapper';
import Dots from './component/dots';
import './index.css';

interface CarouselProps {
    speed?: number,//轮播间隔 0：不轮播
    autoplay?: boolean, //是否自动播放
    isNeedDots?: boolean,//是否需要指示器
    onSwitch?: (index: number) => void
    children?: ReactNode
}

const Carousel: FC<CarouselProps> = ({
    speed = 0,
    autoplay = true,
    isNeedDots = true,
    onSwitch,
    children
}) => {

    const [slideCurrent, setSlideCurrent] = useState<number>(0);
    const timerId = useRef<ReturnType<typeof setTimeout>>();
    const carouselRef = useRef<HTMLDivElement>(null);
    const { slideWidth } = useContext(SliderWrapperContent);
    
    const childrenCount: number = React.Children.count(children);

    // 轮播事件
    const onEvent = useCallback((type: EventType,targetIndex?:number) => {
        switch (type) {
            //停止
            case 'stop':
                if (timerId.current) {
                    clearInterval(timerId.current);
                }
                break;
            //播放
            case 'play':
                if (carouselRef.current && typeof targetIndex === 'number') {
                    // 为了解决组件刷新时也会执行transition，让Css的transition跟随切换时控制
                    carouselRef.current.style.transition = "all 0.4s ease-out";

                    carouselRef.current.style.transform = `translateX(-${slideWidth * targetIndex}px)`;
                    setSlideCurrent(targetIndex);
                    onSwitch?.(targetIndex);
                }
                break;
            default:
                break;
        }
    }, [onSwitch,slideWidth])

    //接收指示点的点击切换
    const handleDotChange = useCallback((index: number) => {
        onEvent('stop');
        onEvent('play',index);
    }, [onEvent]);


    const pageInit = useCallback(() => {
        if(!speed){return }
        if (autoplay) {
            //目标页的索引
            const targetIndex = slideCurrent + 1 === childrenCount ? 0 : slideCurrent + 1;
            clearInterval(timerId.current);
            timerId.current = setInterval(() => onEvent('play',targetIndex), speed);
        }
    }, [onEvent, speed, autoplay, childrenCount, slideCurrent])
    

    useEffect(() => {
        //监听鼠标移入移出
        const handleEnter = () => onEvent('stop');
        if (carouselRef.current) {
            carouselRef.current.addEventListener('mouseenter', handleEnter);
            carouselRef.current.addEventListener('mouseleave', pageInit);
        }
        return () => {
            if (carouselRef.current) {
                carouselRef.current.removeEventListener('mouseenter', handleEnter);
                // eslint-disable-next-line react-hooks/exhaustive-deps
                carouselRef.current.removeEventListener('mouseleave', pageInit);
            }
        }
    }, [onEvent, pageInit])
    
    useLayoutEffect(() => {
        pageInit();
    },[pageInit])

    //兼容无子元素的情况
    if (!children) {
        return null
    }

    return (
        <>
            <div className="carousel" style={{ width: `${childrenCount * slideWidth}px` }} ref={carouselRef}>
                {
                    React.Children.map(children, (child, index) => (
                        <div key={`slide_${index}`} className="carousel-slide" style={{ width: `${slideWidth}px` }}>
                            {child}
                        </div>
                    ))
                }
            </div>
            {
                isNeedDots && <Dots slideCurrent={slideCurrent} count={childrenCount} onChange={handleDotChange} />
            }
        </>

    )
}

export default widthWrapper(Carousel);