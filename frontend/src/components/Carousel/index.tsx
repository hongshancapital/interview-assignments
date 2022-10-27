import React, { useEffect, useState, useMemo, useCallback } from "react"
import SlickList from "../SlickList"
import './index.css'

interface Props {
    children: React.ReactElement[]
    interval?: number
    activeIndex?: number
    onChange?: (currentIndex: number) => void
}

const defaultIntervel = 4000
const getWidth = (num: number) => num * 100 + '%'

const Carousel = (props: Props) => {
    const {
        children,
        interval = defaultIntervel,
        activeIndex = 0,
        onChange = () => {}
    } = props

    const [currentIndex, setIndex] = useState(activeIndex % children.length)

    const length = children.length
    const style = useMemo(() => {
        return {
            width: getWidth(length),
            left: `-${getWidth(currentIndex)}`
        }
    }, [length, currentIndex])

    const updateIndex = useCallback((index: number) => {
        index = index % length
        setIndex(index)
        onChange(index)
    }, [setIndex, onChange, length])

    useEffect(() => {
        const timer = setTimeout(() => {
            updateIndex(currentIndex + 1)
        }, interval)

        return () => clearTimeout(timer)
    }, [interval, currentIndex, updateIndex])

    if (!length) {
        console.error('Child component is required in Carousel')
        return null
    }

    return (
        <div className="carousel">
            <div className="carousel-inner" style={style}>
                {children.map((child, index) => (
                    <div className="carousel-item"key={child.key || index}>
                        {child}
                    </div>
                ))}
            </div>

            <div className="slick-list">
                <SlickList
                    total={length}
                    activeIndex={currentIndex}
                    interval={interval}
                    onChoose={updateIndex}
                />
            </div>
        </div>
    )
}

export default Carousel;
