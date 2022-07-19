import React, { ReactElement, useCallback, useEffect, useRef, useState } from "react";
import './index.css'

interface CarouselProps {
    carouseItemList: Array<ReactElement>, // 子元素列表
    duration: number, // 动画时长
    delay:number // 延迟时长
}

export default function Carousel(props: CarouselProps) {

    const { carouseItemList, duration, delay } = props

    const [position, setPosition] = useState(0)

    // 需要执行动画组件的引用
    const carouselFrameRef = useRef(null)

    // 指示器引用
    const indicatorWrapRef = useRef(null)

    // 指示器执行动画
    const translateIndicator = useCallback(()=> {
        if(indicatorWrapRef.current) {
            const indicatorWrap = indicatorWrapRef.current as HTMLElement
            indicatorWrap.childNodes.forEach((indicator, index)=>{
                const indicatorChild = indicator.firstChild as HTMLElement
                indicatorChild.style.transitionDelay = '0s'
                if(index === position) {
                    indicatorChild.style.transform = 'translateX(0%)'
                    indicatorChild.style.transitionDuration = `${(delay+duration)/1000}s`
                    indicatorChild.style.transitionTimingFunction = "ease"
                } else {
                    indicatorChild.style.transform = 'translateX(-100%)'
                    indicatorChild.style.transitionDuration = "0s"
                }
            })
        }
    },[delay,duration, position])

    // item执行轮播动画
    const translateCarouselItem = useCallback(()=>{
        if(carouselFrameRef.current) {
            const ele = carouselFrameRef.current as HTMLElement
            const tx = position * 100
            ele.style.transform = `translateX(-${tx}%)`
        }
    },[position])

    // timeout引用
    const timeoutRef = useRef(setTimeout(()=>{}))

    // 当position发生变化，执行轮播动画
    useEffect(()=>{
        translateCarouselItem()
        translateIndicator()
        let nextP = position + 1
        if(nextP >= carouseItemList.length) {
            nextP = 0
        }
        if(timeoutRef.current) {
            clearTimeout(timeoutRef.current)
        }
        timeoutRef.current = setTimeout(()=>{
            setPosition(nextP)
        }, duration + delay)
        return ()=>{
            if(timeoutRef.current) {
                clearTimeout(timeoutRef.current)
            }
        }
    },[position, carouseItemList, delay, duration, translateCarouselItem, translateIndicator])

    // carouseFrame样式
    const carouselFrameStyle = {
        transitionDuration: `${duration/1000}s`,
        transitionTimingFunction: "ease",
    }

    return <div className="carousel">
        <div ref={carouselFrameRef} className="carousel-frame" style={carouselFrameStyle}>
            {/* 轮播图 */}
        {carouseItemList.map((item, index) => {
            const translatex = index * 100
            const styles = {transform: `translate(${translatex}%, 0px)`}
            return <div className="carousel-frame-item" style={styles} key={index}>{item}</div>
        })}
        </div>
        {/* 指示器 */}
        <div className="indicator-wrap" ref={indicatorWrapRef}>
            {carouseItemList.map((_, index) => {
                return <div key={index} className="indicator">
                    <div className="indicator-child"/>
                </div>
            })}
        </div>
    </div>
}
