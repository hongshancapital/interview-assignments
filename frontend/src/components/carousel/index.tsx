import React, { useState, useEffect } from 'react'
import Indicator from './indicator'
import style from './index.module.scss'

export interface Props {
    children: React.ReactNode[]
    duration?: number // ms
}

function Carousel({ children, duration = 3000 }: Props) {
    const maxIndex: number = children.length - 1
    const [currIndex, setCurrIndex] = useState(0)

    useEffect(() => {
        setTimeout(() => {
            setCurrIndex(currIndex === maxIndex ? 0 : currIndex + 1)
        }, duration)
    }, [currIndex])

    return (
        <div className={style.main}>
            <div className={style.slides} style={{ transform: `translateX(${currIndex * -100}%)` }}>
                {children}
            </div>
            <div className={style.indicator}>
                <Indicator count={children.length} index={currIndex} duration={duration} />
            </div>
        </div>
    )
}

export default Carousel
