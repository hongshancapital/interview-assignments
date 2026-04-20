import type { CarouselItemType } from './types';

import styles from './index.module.css';
import React, { useState, useEffect } from 'react';

// 幻灯片容器：名称、描述、图片
const CarouselItem: React.FC<{ item: CarouselItemType }> = ({ item }) => {
    return (
        <div className={styles['carousel-item']} style={{ background: item.background }}>
            <div className={styles['info']} style={{ color: item?.theme === 'light' ? '#fff' : '#111' }}>
                <div className={styles['info-name']}>{item.name}</div>
                <div className={styles['info-desc']}>{item.describe}</div>
            </div>
            <div className={styles['image']}>
                <img className={styles['image-inner']} src={item.image} alt='empty' />
            </div>
        </div>
    )
}

// 走马灯（轮播图）组件
const Carousel: React.FC<{ carouselData: CarouselItemType[] }> = ({ carouselData }) => {
    const [activeI, setActiveI] = useState<number>(0)
    // 设置当前最新的幻灯片索引
    const toChangei = (i: number) => {
        if (i >= carouselData.length || i < 0) i = 0
        setActiveI(i)
    }
    // 初始化直接定时轮播幻灯片组件
    useEffect(() => {
        const interval = setInterval(() => toChangei(activeI + 1), 3000)
        return () => {
            if (interval) clearInterval(interval)
        }
    })

    return (
        <div className={styles['container']}>
            <div
                className={styles['carousel-box']}
                style={{ transform: `translateX(-${activeI}00%)`, transitionDuration: activeI === 0 ? '0.3s' : '' }}
            >
                {carouselData.map(item => <CarouselItem key={item.id} item={item} />)}
            </div>
            <div className={styles['scroll-box']}>
                {carouselData.map((item, i) => {
                    return (
                        <div
                            key={item.id}
                            className={styles['scroll-normal']}
                            style={{ background: carouselData[activeI]?.theme === 'light' ? '#eee' : '#aaa' }}
                            onClick={() => { toChangei(i) }}
                        >
                            {i === activeI ? <div className={styles['scroll-active']}></div> : ''}
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default Carousel
