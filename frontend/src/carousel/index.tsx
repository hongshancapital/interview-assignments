import React, { useEffect, useState } from 'react'
import {SlideContent} from '../interface/carousel-interface'
import Slide from './components/slide'

import './index.css'

interface Props {
    duration?: number
    transitionDuration?: number
    slides: SlideContent[]
}

const Carousel: React.FC<Props> = (props) => {
    const { 
        duration = 3000,
        transitionDuration = 1000,
        slides= []
    } = props

    /** current index of slide */
    const [currentIndex, setCurrentIndex] = useState(0)

    useEffect(() => {
        let count = 0
        const timer = setInterval(() => {
            setCurrentIndex(++count % slides.length)
        }, duration)

        return () => {
            clearInterval(timer)
        }
    }, [slides.length, duration, transitionDuration])

    return (
        <div className='carousel'>
            <div
                className='carousel-wrapper'
                style={{
                    transitionDuration: transitionDuration + 'ms',
                    transform: `translateX(${-currentIndex * 100}%)`
                }}
            >
                {/* content of slide */}
                { slides.length ? slides.map((slide, index) => {
                    return (
                        <div key={index} className='carousel-item'>
                            <Slide slide={slide} />
                        </div>
                    )
                }) : null }
            </div>
            {/* indicator */}
            {/* so the type of indicator can be repalced in the future */}
            <div className='indicator-wrapper'>
                { slides.map((slide, index) => {
                    return (
                        <div
                            key={index}
                            className={`indicator-item ${index === currentIndex ? 'active' : ''}`}
                            style={{ animationDuration: duration + 'ms' }}
                        ></div>
                    )
                }) }
            </div>
        </div>
    )
}

export default Carousel