import { CSSProperties, ReactNode, useEffect, useState } from 'react'
import './index.css'
import ProgressBar from './ProgressBar'

interface CarouselItem {
    className?: string
    style?: CSSProperties
    content: ReactNode
}
export interface CarouselProps {
    /** 轮播卡片 */
    list: CarouselItem[]
    /** 轮播时间，单位ms。默认为3000 */
    duration?: number
}
export default function Carousel(props: CarouselProps) {
    const { list, duration = 3000 } = props
    const [index, setIndex] = useState(0)
    // 自动轮播
    useEffect(() => {
        const timer = setTimeout(() => {
            setIndex((index + 1) % list.length)
        }, duration)
        return () => clearTimeout(timer)
    }, [list, duration, index])

    return <div className='_carousel'>
        <div className="_list" style={{ left: `-${index * 100}%` }}>
            {list.map((o, index) => <div
                key={index}
                className={"_card " + (o.className || '')}
                style={o.style}
            >{o.content}</div>)}
        </div>
        {list.length>1 && <ProgressBar size={list.length} index={index} duration={duration} />}
    </div>
}