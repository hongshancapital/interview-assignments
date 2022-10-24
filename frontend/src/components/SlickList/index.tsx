import React from 'react'
import SlickItem from './SlickItem'

interface Props {
    total: number
    activeIndex: number
    interval: number
    onChoose?: (activeIndex: number) => void
}

const SlickList = (props: Props) => {
    const {
        total,
        interval,
        activeIndex,
        onChoose = () => {}
    } = props
    
    return (
        <>
            {Array.from({ length: total }).map((_, index) => (
                <SlickItem
                    key={index}
                    isActive={index === activeIndex}
                    currentIndex={index}
                    interval={interval}
                    onClick={onChoose}
                />
            ))}
        </>
    )
}

export default SlickList