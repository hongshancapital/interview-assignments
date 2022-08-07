import React, { useEffect, useState, useRef } from 'react'
import { useSize, useLatest } from 'ahooks'
import styles from './index.module.css'
import { CarouselProps } from './type'

const Carousel = (props: CarouselProps) => {

    const { children, interval = 1 } = props

    const [currentIndex, setCurrentIndex] = useState<number>(0)

    const currentIndexRef = useLatest(currentIndex)
    const contentRef = useRef(null)
    const { width = 0 } = useSize(contentRef) || {}

    const len = children.length

    useEffect(() => {
        if (width === 0 ) return
        let move = setInterval(() => {
            if (currentIndexRef.current === (len - 1)) {
                setCurrentIndex(0)
            }
            else {
                setCurrentIndex((v) => v + 1)
            }
        }, interval * 1000)

        return () => {
            clearInterval(move)
        }
    }, [width])

    // 轮播部分
    const renderContent = () => {
        return <div className={styles.contentWrapper} ref={contentRef} style={{ left: `-${currentIndex * width}px` }}>
            {
                children.map((child: JSX.Element, index: number) => {
                    return <div className={styles.carouselChildWrapper} key={index} style={{ left: `${index * width }px`}}>
                        {child}
                    </div>
                })
            }
        </div>
    }

    // 下端进度条
    const renderIndexPanel = () => {
        return <div className={styles.indexPanel}>
            {
                children.map((child: JSX.Element, index: number) => {
                    let isActive = index === currentIndex
                    return <div className={styles.progress} key={index}>
                        {
                            isActive && <div className={styles.moveProgress} style={{ animationDuration: `${interval}s` }}/>
                        }
                    </div>
                })
            }
        </div>
    }

    if (len === 0) return null

    return <div className={styles.carouselContainer}>
        {renderContent()}
        {renderIndexPanel()}
    </div>
}

export default Carousel