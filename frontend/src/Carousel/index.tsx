import React, { useEffect, useState, useRef, useMemo } from 'react';
import { ICarouselProps, CarouselItem } from './types';
import styles from './index.module.css';
import Item from "./Item";

const pageStyle = (config: CarouselItem): object => ({
    width: '100vw',
    height: '100vh',
    color: config.color,
    background: `center / contain no-repeat url("${config.img}"), ${config.bgColor}`,
    display: 'flex',
    flexDirection: 'column'
})

const createItems = (input: CarouselItem[]) => {
    if (!input || input.length === 0) {
        return [];
    }
    return input.map((config, index) => {
        return (
            <div key={index} style={pageStyle(config)}>
                <div style={{ fontWeight: 'bold', fontSize: '60px', marginTop: '15%' }}>
                    {
                        config.textList.map((title, index) => <Item key={index}>{title}</Item>)
                    }
                </div>
                <div style={{ fontSize: '22px' }}>
                    {
                        config.sub.map((text, index) => <Item key={index}>{text}</Item>)
                    }
                </div>
            </div>
        )
    })
}

const Carousel: React.FC<ICarouselProps> = (props) => {
    const {
        scrollDirection = 'column',
        initialPage = 0,
        config
    } = props;

    const contentRef = useRef<HTMLDivElement>(null);
    const [currentPage, setCurrentPage] = useState<number>(initialPage);
    const [contentWidth, setContentWidth] = useState<number>(document.documentElement.clientWidth);
    const items = useMemo(() => createItems(config), []);

    const getDomRef = () => contentRef.current as HTMLDivElement;
    const apply = (moveX: number) => {
        return getDomRef().style.transform = `translate3d(${moveX}px,0px,0px)`
    }

    const forward = () => {
        getDomRef().style.transition = ".3s ease all"
        const offset = -(currentPage + 1) * contentWidth
        apply(offset)
    }

    const restart = () => {
        getDomRef().style.transition = `${.3 * config.length}s ease all`;
        apply(0);
    }

    useEffect(() => {
        setContentWidth(document.documentElement.clientWidth);
        setTimeout(() => {
            if (currentPage === config.length - 1) {
                setCurrentPage(0)
                restart()
            } else {
                setCurrentPage(currentPage + 1)
                forward()
            }
        }, 3000);
    }, [currentPage]);
    return (
        <div className={styles.wrapper}>
            <div ref={contentRef} className={styles.content} style={{
                flexDirection: scrollDirection,
                width: `${(config.length + 1) * 100}%`,
            }}>
                {items}
            </div>
            <div className={styles.dots}>
                {
                    items.map((_, index) => (
                        <div key={index} className={styles.dot}>
                            <div className={currentPage === index ? styles.animate : styles.load}></div>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default Carousel;