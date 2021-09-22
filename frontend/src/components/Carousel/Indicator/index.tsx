import React, { useState, useEffect } from 'react';

import styles from './Indicator.module.css';

function Indicator(props: { imgList: string[]; activeIndex: number, interval: number }) {
    const { imgList, activeIndex = 0, interval } = props;
    const [isMounted, setIsMounted] = useState(false);

    useEffect(() => {
        setIsMounted(true);
    }, []);

    return (
        <div className={styles['indicator-wrap']}>
            {imgList.map((_, index) => (
                <div key={index} className={[styles['indicator'], isMounted && index === activeIndex ? styles['active'] : null].join(' ')}>
                    <div className={styles['indicator-progress']} style={{ transitionDuration: `${interval}ms` }}></div>
                </div>
            ))}
        </div>
    )
}

export default Indicator;