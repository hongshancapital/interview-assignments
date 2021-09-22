import React, { useState, useEffect } from 'react';

import Indicator from './Indicator';

import styles from './Carousel.module.css';


function Carousel(props: {
    imgList?: string[];
    interval?: number;
}) {
    const { imgList = [], interval = 3000 } = props;
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        const timer = setTimeout(() => {
            setCurrentIndex(currentIndex + 1 >= imgList.length ? 0 : currentIndex + 1);
        }, interval);
        return () => clearTimeout(timer);
    }, [currentIndex]);

    useEffect(() => {
        setCurrentIndex(0);
    }, [imgList.length, interval]);

    return (
        <div className={styles['carousel']}>
            <div className={styles['picture-wrap']} style={{ left: -currentIndex * 100 + '%' }}>
                {imgList.map((src, index) => (
                    <img key={index} className={styles['picture']} src={src}></img>
                ))}
            </div>
            <Indicator imgList={imgList} activeIndex={currentIndex} interval={interval}></Indicator>
        </div>
    );
}

export default Carousel;