import React, { useEffect, useState } from "react";
import './index.scss';

interface CarouselProps {
  content: Item[];
  switchTime: number;
}

interface Item {
  title: string;
  text: string;
  fontColor: string;
  imgUrl: string;
}

function Carousel(props: CarouselProps) {
  const { content, switchTime } = props
  const [current, setCurrent] = useState(0)
  const [firstRender, setFirstRender] = useState(false)

  useEffect(() => {
    const timer = setInterval(() => {
      if(current >= content.length - 1) {
        setCurrent(0)
      } else {
        setCurrent(current + 1)
      }
    }, switchTime)
    setFirstRender(true)
    return () => clearInterval(timer)
  }, [current, switchTime, content.length])

  return (
    <div className="carousel-container">
      <div className="slides" style={{left: `${-1 * current * 100}%`}}>
        {
          content && content.map((item: Item, index: number) => (
            <div className="slide-item" key={index}>
              <p className="title" style={{color: `${item.fontColor}`}}>{item.title}</p>
              <p className="text" style={{color: `${item.fontColor}`}}>{item.text}</p>
              <img src={item.imgUrl} alt=""/>
            </div>
          ))
        }
      </div>
      <div className="progress">
        {
          content && content.map((_, index: number) => (
            <span className="outer" key={index}>
              <span className="inner" style={(firstRender && (index === current)) ? {width: '100%', transition: `width ${switchTime/1000}s linear`} : {}}></span>
            </span>
          ))
        }
      </div>
    </div>
  )
}

export default Carousel;