
import React, { ReactNode } from 'react'
import { useEffect, useRef, useState } from 'react'
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
    const refProgressList = useRef(null)
    const [_curIndex, setCurIndex] = useState(0)

    // 进度条
    useEffect(()=>{
        const len = React.Children.count(children)
        const target = getCurrent(refProgressList).childNodes[_curIndex].childNodes[0]

        // 监听进度条动画结束
        target.onanimationend = ()=>{
            target.className = "progress_inner"
            target.onanimationend = null

            const nextIndex = (_curIndex + 1) % len
            setCurIndex(nextIndex)
        }

        // 进度条添加动画
        target.className += " progress_change"
    },[_curIndex, children])

    function getCurrent(refValue: any){
        return refValue.current as any
    }

    return (
    <div className="root" style={{...size}}>
        <div className="goodlist" 
                style={{transitionDuration: `${switchDuration}ms`, transform: `translateX(-${_curIndex * 100}%)`}}>
            {children}
        </div>

        <div ref={refProgressList} className="progress_list">
            {React.Children.map(children, ()=>{
                return (
                <div className="progress">
                    <div className="progress_inner" style={{animationDuration: `${stayDuration / 1000}s`}}></div>
                </div>)
            })}
        </div>
    </div>)
}

export default Carousel