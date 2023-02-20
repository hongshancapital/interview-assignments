import React, { useRef, useState } from "react";
import { FC } from "react";
import useInterval from "../hooks/useInterval";
import CarouselItem from "./CarouselItem";
import style from './carousel.module.scss';
import { CarouselItemData } from "./interface";
import CarouselBarItem from "./CarouselBarItem";
import classNames from "classnames";

/**
 *
 * 1、支持单页或多页轮播；
 * 2、可控制动画时间；
 * 3、支持通过定义样式 class，控制组件内部元素样式
 * 4、支持自定义组件宽高，宽高的值应当是 css 支持的基本尺寸单位
 */
export interface CarouselProps {
    list: CarouselItemData[];
    interval?: number; // 秒
    width?: string;
    height?: string;
    coustomClassName?: string;
}

const Carousel: FC<CarouselProps> = ({
    list,
    interval = 3,
    width = '100%',
    height = '100%',
    coustomClassName
}) => {
    const [left, setLeft] = useState<number>(0);
    const indexRef = useRef<number>(0);
    const len = list.length;
    const step = parseFloat(width.replace(/[^\d]/g, ''))
    const suffix = width.replace(/\d/g, '');
    useInterval(() => {
        if (len === 1) {
            return false;
        }
        indexRef.current++
        if (indexRef.current === len) {
            indexRef.current = 0
        }
        setLeft(indexRef.current * step)
    }, interval * 1000);

    return (
        <div className={classNames(style.container, coustomClassName)} style={{width, height}}>
            <div className={style.carousel_wrapper} style={{ left: `-${left}${suffix}`}}>
                {list.map(item => <CarouselItem key={ item.url } data={item} width={width} />) }
            </div>
            {len > 1 && <div className={style.bar}>
                {list.map((item, index) => {
                    return (
                        <CarouselBarItem
                            key={item.url}
                            animationDuration={interval}
                            animationStart={index === indexRef.current}
                        />
                    )
                })}
            </div>
            }
        </div>
    )
}

export default Carousel;