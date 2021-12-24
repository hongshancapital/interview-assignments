import React, { ReactElement, useEffect, useState } from "react";
import "./index.css"
import { getIdentifierAnimationStyle, getSlickPositionLeft } from "./tools";

interface Props {
    children: Array<ReactElement>, // the children's length should be more than 1
    duration?: number // unit is millisecond, default value is 2500
    className?: string
}

let timer: number

function Carousel({ children, duration = 2500, className }: Props) {
    const [active, setActive] = useState(-1)

    useEffect(() => {
        setActive(0) // set first animation when comp did mount
    }, [])

    useEffect(() => {
        if (timer) {
            clearTimeout(timer)
        }

        timer = window.setTimeout(() => {
            const newActive = (active + 1) % children.length // if next is equal with children.length, active will return 0
            setActive(newActive)
        }, duration)

        return () => clearTimeout(timer) // when comp destroyed, clear the timer
    }, [active, children.length, duration])

    const onIdentifierClick = (index: number) => {
        setActive(index)
    }

    return (
        <div className={`carousel-wrap w-full h-full relative${className ? ` ${className}` : ''}`}>
            <main className="carousel-main w-full h-full relative">
                {children.map((child, index) => {
                    return <section className="carousel-slick w-full h-full absolute" key={index} style={{ left: getSlickPositionLeft(index, active) }}>
                        {child}
                    </section>
                })}
            </main>
            <footer className="carousel-identifier-wrap w-full absolute">
                {children.map((child, index) => {
                    return <span className="carousel-identifier-outer relative round" key={index} onClick={() => { onIdentifierClick(index) }}>
                        <span className="carousel-identifier-inner round h-full absolute" style={index === active ? getIdentifierAnimationStyle(duration) : undefined}></span>
                    </span>
                })}
            </footer>
        </div>
    );
}

export default Carousel;

interface SliderProps {
    className?: string
    title: string
    description?: string
}

export function Slider({ className, title, description }: SliderProps) {
    return (
        <main className={`main-wrap w-full h-full${className ? ` ${className}` : ''}`}>
            <header className="title">
                {title}
            </header>
            <article className="text">
                {description}
            </article>
        </main>
    )
}
