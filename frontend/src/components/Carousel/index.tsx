import React, { useEffect, useRef, useState } from "react";
import './index.css'

export interface CarouselProps {
    /** 是否自动播放 */
    autoplay?: boolean
    /** 延迟时间，ms */
    delay?: number
    /** 子节点 */
    children: React.ReactNode[] | React.ReactNode
}

const Carousel: React.FC<CarouselProps> = (props) => {
    const { children: _children, autoplay, delay = 3000 } = props
    const children = Array.isArray(_children) ? _children : [_children]
    const [index, setIndex] = useState(0)
    // 用来触发首次进来横条动画
    const [animationReady, setAnimationReady] = useState(false)
    const timerRef = useRef<NodeJS.Timeout | null>(null)

    const maxLength = children.length

    /** 自动滚动 */
    useEffect(() => {
        timerRef.current && clearInterval(timerRef.current)
        if (autoplay && maxLength !== 1 ) {
            timerRef.current = setInterval(() => {
                setIndex((index) => ((index + 1) % maxLength))
            }, delay)
            setAnimationReady(true)
        }
        return () => {
            timerRef.current && clearInterval(timerRef.current)
        }

    }, [autoplay])

    return <div className="carousel">
        <div className="carousel-row" style={{
            width: `${children.length}00%`,
            transform: `translate3d(-${100 / maxLength * index}%,0,0)`
        }}>
            {(children || []).map((item, index) => {
                return <div key={index} className="carousel-page" style={{width: `${100/maxLength}%`}}>
                    {item}
                </div>
            })}
        </div>
        <div className="carousel-nav">
            {
                (children || []).map((_, i) => <div key={i} className="carousel-nav-item">
                    <div className="carousel-nav-item-focus"
                        style={{
                            transitionDuration: `${index === i ? delay : 0}ms`,
                            transform: `translate3d(${index === i && animationReady ? '0' : '-100%'}, 0, 0)`
                        }}
                    />
                </div>)
            }
        </div>
    </div>
}

export default Carousel