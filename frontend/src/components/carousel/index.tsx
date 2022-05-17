import React, { useState, useEffect } from "react";
import Dot from './components/dot';
import Item from './components/item';
import { CarouselPros }  from "./interface";
import './index.scss';

const prefix = 'carousel'
const Carousel = (props: CarouselPros) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const { list = [] ,duration = 3000, animationTime = 0.3 } = props;
  
  useEffect(() => {
    setActiveIndex(0)
    const timer = setInterval(() => {
      setActiveIndex(currentIndex => (currentIndex + 1) % (list.length || 1))
    }, duration)
    return () => {
      clearInterval(timer)
    }
  }, [list, duration])


  const carouselWrapWidth = window.screen.width || 0; // 获取浏览器宽度
  const translateWidth = carouselWrapWidth * activeIndex; // 位移的距离

  return (
    <div
      className={`${prefix}-wrap`}
    >
      <div
        style={{
          transform: `translateX(${-translateWidth}px)`,
          display: "flex",
          transition: `all linear ${animationTime}s`
        }}
      >
      <div className={`${prefix}-item`}>
        {list.map((item,index)=>{
          return <Item title={item.title} subTitle={item.subTitle} imgType={item.imgType} theme={item.theme} key={index+'item'} />
        })}
      </div>
      </div>
      <div className={`${prefix}-dot`} >
        <Dot
          total={list.length}
          active={activeIndex}
          animationTime={duration / 1000}
        />
      </div>
    </div >
  )
}

export default Carousel; 