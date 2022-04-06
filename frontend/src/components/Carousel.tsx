import React, { useEffect, useRef, useState } from "react";
import { FC } from "react";
import useInterval from "../hooks/useInterval";
import CarouselItem, { CarouselItemProps } from "./CarouselItem";
import style from './carousel.module.scss';
import classNames from 'classnames';

export interface CarouselProps {
    list: CarouselItemProps[];
    interval?: number; // 秒
}

const Carousel: FC<CarouselProps> = ({
    list,
    interval = 3
}) => {
    const [rect, setRect] = useState<DOMRect>(); 
    const [left, setLeft] = useState<number>(0);
    const [barAni, setBarAni] = useState<boolean[]>([]);
    const ref = useRef<HTMLDivElement>(null);
    const indexRef = useRef<number>(0);
    const len = list.length;
    useInterval(() => {
        if (indexRef.current === len) {
            indexRef.current = 0
        }
        setLeft(indexRef.current * (rect?.width ?? 0))
        const bl = [...barAni];
        bl.splice(indexRef.current, 1, true)
        setBarAni([...bl])
        indexRef.current++
    }, interval * 1000);
 
    useEffect(() => {
        if (ref?.current) {
            setRect(ref.current.getBoundingClientRect())
        }
    }, [ref?.current])

    return (
        <div className={style.container} ref={ref}>
            <div className={style.carousel_wrapper} style={{width: len * (rect?.width ?? 0), left: `-${left}px`}}>
                { list.map(item => {
                    const { url, alt, title, desc, style = {} } = item
                    return <CarouselItem 
                        style={{width: rect?.width, height: rect?.height, ...style}} 
                        key={url} 
                        url={url} 
                        alt={alt}
                        title={title}
                        desc={desc}
                    />
                }) }
            </div>
            <ul className={style.bar}>
                { list.map((item, index) => {
                    return (
                        <li 
                            key={item.url} 
                            className={style.bar_item} 
                        >
                            <span 
                                className={classNames({
                                    [style.bar_item_inner]: true,
                                    [style.bar_item_inner_ani]: barAni?.[index]
                                })} 
                                onAnimationEnd={() => {
                                    const status = barAni;
                                    status.splice(index, 1, false)
                                    setBarAni([...status])
                                }}
                            >
                            </span>
                        </li>
                    )
                })}
            </ul>
        </div>
    )
}

export default Carousel;