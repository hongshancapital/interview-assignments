import React, { useEffect, useRef, useState } from "react"

import './carousel.scss'

export interface ICarousel {
  data: {
    title: string | string[];
    desc: string[];
    img: string;
    style: {
      color: string;
      backgroundColor: string;
    }
  }[]
}


function addCurrentClass(current: number, elem: HTMLElement, className: string, newClassName: string) {
  const elements = elem.querySelectorAll('.'+className)
  Array.from(elements).forEach((v: any, k: number) => {
    if (current === k) {
      elements[k].classList.add(newClassName)
    } else {
      elements[k].classList.remove(newClassName)
    }
  })
}

export const Carousel = (props: ICarousel) => {
  let myRef = useRef<HTMLDivElement>(null), myRef2 = useRef<HTMLDivElement>(null);
  const [current, setCurrent] = useState(0)
  const { data } = props

  const length = data.length

  function fadeOutLeft(current: number, elem: HTMLElement, iterations: number) {
    var keyframes = [
      {transform: `translateX(-${(((current - 1) % length)/length) * 100}%)`, offset: 0}, 
      {transform: `translateX(-${((current % length)/length) * 100}%)`, offset: 1}, 
    ];
    
    var timing = {
      duration: 1000,
      iterations: iterations
    };
    return elem.animate(keyframes, timing);
  }

  useEffect(() => {
    if (myRef2 && myRef2.current) {
      addCurrentClass(current % length, myRef2.current, 'yy-carousel-bar-span', 'yy-carousel-active')
    }
    let timer = setInterval(() => {
      setCurrent(v => {
        if (myRef && myRef.current) { fadeOutLeft(v+1, myRef.current, 1); }
        return v + 1
      })
    }, 3000)

    return () => {
      clearInterval(timer)
    }
  })
  return (<div id="yy-carousel">
      <div
        className="yy-carousel"
        ref={myRef}
        style={{
          width: `${data.length * 100}%`,
          transform: `translateX(-${((current % length)/length) * 100}%)`
        }}
      >
      {data.map((v, k) => {
        const { img = '', desc = [], style: { backgroundColor = '#fff', color = '#000' }} = v
        return <div
          className="yy-carousel-item"
          style={{
            backgroundImage: `url(${img})`,
            backgroundColor,
            color
          }}
          key={k}>
          <h2 className="yy-carousel-title">{v.title}</h2>
          {desc.map((val, key) => <p className="yy-carousel-desc" key={key}>{val}</p>)}
        </div>
      })}
    </div>
    <div className="yy-carousel-speed-bar">
      <div className="yy-carousel-bar-line-group" style={{
        width: `${length * 50}px`
      }}>
        <div ref={myRef2} className="yy-carousel-bar-line">
          {data.map((v, k) => <span className="yy-carousel-bar-item" key={k}>
            <span className={`yy-carousel-bar-span`}>
            </span>
          </span>)}
        </div>
      </div>
    </div>
  </div>)
}