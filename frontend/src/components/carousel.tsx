import React, { useEffect, useState, ReactElement, useRef } from "react";
import './carousel.css'

export interface CarouselProps {
    items: {
      title: ReactElement;
      desc?: ReactElement;
      url: string;
      textColor?: string;
      bgColor?: string;
    }[];
    /** 轮播间隔，单位：毫秒，默认值3000 */
    duration?: number;
}

const delay = 1000;

export function Carousel(props: CarouselProps) {
  const { duration = 3000, items = [] } = props;
  const [sliderContents] = useState(items);
  const totalCount = sliderContents.length;
  // 当前索引
  const [activeIndex, setActiveIndex ] = useState(0);
  // 是否重置首帧位置
  const [posXinit, setPosXinit] = useState(false);
  // 是否开始动画
  const started = useRef(false);
  useEffect(() => {
    // 调整动画时间，末尾添加了一张图，需要调整这个时间
    const time = started.current && activeIndex === 0 ? duration - delay : duration;
    const timer = setInterval(() => {
      setPosXinit(false);
      const next = activeIndex === totalCount ? 0 : activeIndex + 1;
      setActiveIndex(next);
      if (next === totalCount) {
        // 最后一张图和首图之间的流畅性
        setTimeout(() => {
          setPosXinit(true)
          setActiveIndex(0);
        }, delay)
      }
    }, time);
    started.current = true;
    return () => clearInterval(timer);
  }, [activeIndex, duration, totalCount]);
  return (
    <div className="carousel">
      <div
        className='slider-wrapper'
        style={{ 'transform': `translate3d(-${activeIndex * 100}vw, 0, 0)`, 'transition': `${posXinit ? 'none' : 'all 1s ease-in-out'}` }}>
        { [...sliderContents, sliderContents[0]].map((item, index) => 
          (
            <div key={index} className="slider-item" style={{ 'backgroundColor': `${item.bgColor || '#fff'}`, 'color': `${item.textColor || '#000'}`}}>
              <div className="text">
                { item.title }
                { item.desc }
              </div>
              <img src={ item.url } alt="" />
            </div>
          )
        )}
      </div>
      <div className="slider-bar-wrapper">
          {
            Array(totalCount).fill('').map((item, index) => {
              const active = activeIndex === index || (index === 0 && activeIndex === totalCount);
              return (
                <div className="bar-wrapper" key={index}>
                  <div
                    className={`bar ${active ? 'active-bar' : ''}`}
                    style={{ animationDuration: `${duration}ms` }}
                  ></div>
                </div>
              )
            })
          }
      </div>
    </div>
  );
}