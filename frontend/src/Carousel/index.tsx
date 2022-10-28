
import React, { ReactNode } from 'react'
import { useState } from 'react'
import './index.css'

interface CarouselProps{
    stayDuration: number           // milliseconds
    switchDuration: number         // milliseconds
    size?:{                         // width && height; default: 100% && 100%
        width?: string
        height?: string
    }                          
    children: ReactNode
}
function Carousel({stayDuration, switchDuration, size, children} : CarouselProps){
    const [_curIndex, setCurIndex] = useState(0)
    const len = React.Children.count(children)

    const animationEndCallback = ()=>{
        setCurIndex((oldIndex)=> (oldIndex + 1) % len) 
    }

    return (
    <div className="root" style={{...size}}>
        <div className="goodlist" 
                style={{transitionDuration: `${switchDuration}ms`, transform: `translateX(-${_curIndex * 100}%)`}}>
            {children}
        </div>

        <div className="progress_list" onAnimationEnd={animationEndCallback}>
            {React.Children.map(children, (child, index)=>{
                return (
                <div className="progress">
                    <div className={`progress_inner ${_curIndex === index ? 'progress_change': ''}`} style={{animationDuration: `${stayDuration / 1000}s`}}></div>
                </div>)
            })}
        </div>
    </div>)
}

export default Carousel