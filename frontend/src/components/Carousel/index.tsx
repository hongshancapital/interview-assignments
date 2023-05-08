import React, { useCallback, useEffect, useRef, useState } from "react"

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


export const Carousel = (props: ICarousel) => {
  let myRef = useRef<HTMLDivElement>(null),
  myRef2 = useRef<HTMLDivElement>(null);

  const [current, setCurrent] = useState(0)
  const { data } = props

  const length = data.length;
  const itemWidth = 100 / length;

  const addCurrentClass = useCallback((current: number, elem: HTMLElement, newClassName: string) => {
    const children = elem.children;
    const len = children.length;
    for (let i = 0; i < len; i++) {
      const child = children[i].firstChild as HTMLElement;
      const isCurrent = current === i;
      if (child.classList.contains(newClassName) !== isCurrent) {
        child.classList.toggle(newClassName);
      }
    }
  }, []);

  const fadeOutLeft = useCallback((current: number, elem: HTMLElement, iterations: number) => {
    const keyframes = [
      { transform: `translateX(-${(((current - 1) % length) * itemWidth)}%)`, offset: 0 },
      { transform: `translateX(-${((current % length) * itemWidth)}%)`, offset: 1 },
    ];
    const timing = { duration: 1000, iterations };
    return elem.animate(keyframes, timing);
  }, [length, itemWidth]);


  useEffect(() => {
    console.log('myRef?.current', myRef?.current, myRef2.current);
    if (myRef2?.current) {
      addCurrentClass(current % length, myRef2.current, 'yy-carousel-active');
    }
    const timer = setInterval(() => {
      setCurrent((v) => {
        if (myRef?.current) {
          fadeOutLeft(v + 1, myRef.current, 1);
        }
        return v + 1;
      });
    }, 3000);

    return () => {
      clearInterval(timer)
    }
  }, [addCurrentClass, current, length, fadeOutLeft]);

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
          {data.map((_v, k) => <span className="yy-carousel-bar-item" key={k}>
            <span className={`yy-carousel-bar-span`}>
            </span>
          </span>)}
        </div>
      </div>
    </div>
  </div>)
}