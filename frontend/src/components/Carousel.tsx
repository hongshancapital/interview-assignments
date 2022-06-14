import React, { useEffect, useRef, useState, useLayoutEffect } from 'react'
import './styles/Carousel.css'

interface IsCarouselProps {
  children: JSX.Element[]
}

export default function Carousel(props: IsCarouselProps) {
  const { children } = props
  const [childLen, setChildLen] = useState<number>(0)
  const [activeIndex, setActiveIndex] = useState<number>(-1)
  const translateMain = useRef<HTMLDivElement | null>(null)

  useEffect(() => {
    if (children) {
      setChildLen(children.length)
    }
  }, [children])

  useEffect(() => {
    setActiveIndex(0)
    const timer = setInterval(() => {
      setActiveIndex((pre: number) => {
        let fresh = pre + 1
        if (fresh >= childLen) {
          fresh = 0
        }

        return fresh
      })
    }, 3000)
  
    return () => {
      clearInterval(timer)
    }
  }, [childLen])
  

  useLayoutEffect(() => {
    if (translateMain.current) {
      translateMain.current.style.transform = `translate(-${100 * activeIndex}vw)`
    }
  }, [activeIndex])
  
  return <div className="wrapper-root">
    <div className="translate-main" ref={translateMain}>{children}</div>
    <div className="progress-container">{
      children && children.map((child, index) => {
        return <div className="outer-item" key={index}>
          <div className={`inner-item ${index === activeIndex ? 'active-dot' : ''}`}>
          </div>
        </div>
      })
    }</div>
  </div>
}