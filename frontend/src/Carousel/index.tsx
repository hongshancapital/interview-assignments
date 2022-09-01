
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

    // 所有进度条添加动画监听 和 回收函数
    useEffect(()=>{
        const len = React.Children.count(children)

        const progresses = getCurrent(refProgressList).childNodes
        for(let i = 0; i < progresses.length; i++){
            const current = progresses[i].childNodes[0]
            current.onanimationend = ()=>{
                current.className = "progress_inner"

                setCurIndex((oldIndex)=> (oldIndex + 1) % len) 
            }
        }
        return ()=>{
            for(let i = 0; i < progresses.length; i++){
                const current = progresses[i].childNodes[0]
                current.onanimationend = null
            }
        }
    },[children])

    //给当前的progress添加动画
    useEffect(()=>{
        const len = React.Children.count(children)
        if(_curIndex >= len)    return

        const current = getCurrent(refProgressList).childNodes[_curIndex].childNodes[0]
        current.className += " progress_change"
    }, [_curIndex, children])

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