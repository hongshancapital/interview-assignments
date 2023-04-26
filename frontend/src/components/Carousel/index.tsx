import React, { useCallback, useEffect, useRef, useState } from 'react'
import './index.scss'

interface Props {
    children: React.ReactNode[]
    autoplayInterval?: number
}

interface CarouselItemProps {
    children: React.ReactNode
}
interface IntervalItemProps {
    index: number
    autoplayInterval: number
    activeIndex: number
    setActiveIndex: (index: number) => void
}

const CarouselItem = ({ children }: CarouselItemProps) => {
    return <div className="carousel-item">{children}</div>
}

const IntervalItem = ({ autoplayInterval, activeIndex, index, setActiveIndex }: IntervalItemProps) => {
    const [active, setActive] = useState(false)

    useEffect(() => {
        setActive(false)
        requestAnimationFrame(() => setActive(true))
        return () => {}
    }, [activeIndex])

    return (
        <div
            className="interval-item"
            onClick={() => {
                setActiveIndex(index)
            }}
        >
            <div
                className="inner"
                style={{
                    transitionDuration: activeIndex === index && active ? `${autoplayInterval / 1000}s` : '0s',
                    width: activeIndex === index && active ? '100%' : 0,
                }}
            ></div>
        </div>
    )
}

const Carousel = (props: Props) => {
    const { children } = props
    const active = useRef(0)
    const [activeIndex, setActive] = useState(0)
    let timer = useRef(0)
    const autoplayInterval = props.autoplayInterval || 3000

    const setActiveIndex = (index: number) => {
        if (index === active.current) {
            return
        } else {
            clearTimeout(timer.current)
            active.current = index
            setActive(active.current)
        }
    }

    const autoplay = useCallback(() => {
        if (active.current > children.length - 2) {
            setActiveIndex(0)
        } else {
            setActiveIndex(active.current + 1)
        }
        timer.current = window.setTimeout(autoplay, autoplayInterval)
    }, [children.length, autoplayInterval])

    useEffect(() => {
        clearTimeout(timer.current)
        timer.current = window.setTimeout(autoplay, autoplayInterval)
        return () => {
            clearTimeout(timer.current)
        }
    }, [activeIndex, autoplay, autoplayInterval])

    return (
        <div className="carousel-wrapper">
            <div className="carousel-container" style={{ transform: `translateX(-${100 * active.current}%)` }}>
                {children.map((item, index) => (
                    <CarouselItem key={index}>
                        {item}
                    </CarouselItem>
                ))}
            </div>

            <div className="interval">
                {children.map((_, index) => (
                    <IntervalItem
                        key={index}
                        index={index}
                        autoplayInterval={autoplayInterval}
                        activeIndex={active.current}
                        setActiveIndex={setActiveIndex}
                    ></IntervalItem>
                ))}
            </div>
        </div>
    )
}

export default Carousel
