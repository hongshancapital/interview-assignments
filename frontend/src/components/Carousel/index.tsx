import React, { FC, useState, useRef, useMemo, useCallback, useEffect } from "react";
import CarouselDot from '../CarouselDot'
import useCurrIndex from "../../hooks/useCurrIndex";
import './index.css'

export interface CarouselItem {
    id: number,
    component: FC,
}

interface CarouselProps {
    width?: string | number;
    height?: string | number;
    items: CarouselItem[];
    time?: number;
    speed?: number;
}

// 防抖
const d = (fn: Function, time: number = 200) => {
    let timer: NodeJS.Timeout | null = null;
    return function (...rest: any[]) {
        timer && clearTimeout(timer)
        timer = setTimeout(() => fn(...rest), time)
    }
}

const Carousel: FC<CarouselProps> = (props) => {
    const { width = '100%', height = '100%', items, time = 3000, speed= 0.5 } = props;
    const node = useRef<HTMLDivElement>(null);
    const { index } = useCurrIndex({ indexMax: items.length, time });
    const [itemWidth, setItemWidth] = useState(0);

    // 容器样式
    const containerStyle = useMemo(() => {
        return {
            width,
            height
        };
    }, [height, width]);

    // 滑动容器样式
    const wrapperStyle = useMemo(() => {
        return {
            width: itemWidth * items.length,
            left: -(index * itemWidth),
            transitionDuration: `${speed}s`
        };
    }, [index, itemWidth, items.length, speed]);

    // 单项样式
    const itemStyle = useCallback((index: number) => {
        return {
            left: index * itemWidth,
            width: itemWidth,
            height: height
        };
    }, [height, itemWidth]);

    // 渲染项
    const renderItem = useCallback((item: CarouselItem, index) => {
        const { id, component: Component } = item;
        return (
            <div className="carousel-item" key={id} style={itemStyle(index)}>
                <Component />
            </div>
        );
    }, [itemStyle]);

    // resize 监听
    useEffect(() => {
        const onResize = d((event: Event) => {
            const el = node.current;
            el && setItemWidth(el.clientWidth)
        })
        onResize();
        window.addEventListener('resize', onResize, false)
        return () => {
            window.removeEventListener('resize', onResize, false)
        }
    }, [node])

    return (
        <div className="carousel-container" ref={node} style={containerStyle}>
            <div className="carousel-wrapper" style={wrapperStyle}>
                {items.map(renderItem)}
            </div>
            <CarouselDot index={index} time={time} items={items} />
        </div>
    );
}

export default Carousel;
