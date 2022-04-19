import React, { ReactElement, ReactNode, useEffect, useRef, useState } from "react";
import './Carousel.css'

interface CarouselProps {
    children: Array<ReactElement>,
    interval: number
}

export default function Carousel(props: CarouselProps) {
    const { children, interval } = props

    const [position, setPosition] = useState(0)

    const carouselFrame = useRef(null)

    const timeoutRef = useRef(setTimeout(()=>{}))

    useEffect(()=>{
        timeoutRef.current = next()
    },[position])

    return <div className="carousel">
        <div ref={carouselFrame} className="carousel-frame">
        {children.map((item, index) => {
            const translatex = index * 100
            const styles = {transform: `translate(${translatex}%, 0px)`}
            return <div className="carousel-frame-item" style={styles} key={index}>{item}</div>
        })}
        </div>
        <div>
            {children.map((_, index) => {
                return <div key={index}/>
            })}
        </div>
    </div>

    function next() {
        let nextP = position + 1
        if(nextP >= children.length) {
            nextP = 0
        }
        if(carouselFrame.current) {
            const ele = carouselFrame.current as HTMLElement
            const tx = position * 100
            ele.style.transform = `translateX(-${tx}%)`
            ele.style.transitionDuration = `${interval/1000}s`
            ele.style.transitionTimingFunction = "ease"
        }
        clearTimeout(timeoutRef.current)
        return setTimeout(()=>{
            setPosition(nextP)
        }, interval)
    }
}
