import React, { memo, useState, useEffect, useMemo, type CSSProperties, useCallback } from 'react';
import './index.css'

export interface CarouselListItem {
  serviceUrl: string, // 图片url
  title: Array<string>, // 图片标题
  desc?: Array<string>, // 描述
}

interface CarouselProps {
  data: Array<CarouselListItem>, // 数据
  interval?: number, // 延时
  autoplay?: boolean, // 是否自动播放
  indicatorDots?: boolean, // 是否展示指示点
}

const Carousel: React.FC<CarouselProps> = (props): React.ReactElement => {
  const {
    data = [],
    indicatorDots = true,
    autoplay = true,
    interval = 3000, 
  } = props;
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const [translateValue, setTranslateValue] = useState<number>(0);
  const [auto, setAutoplay] = useState<boolean>(autoplay)

  // 定义定时器
  let intervalCarousel:any;

  // 轮播图滚动器逻辑
  const handleScroll = useCallback(()=>{
    // 获取滚动区域的高度
    const slideWidth: number = (document.querySelector('.carousel') as any)?.clientWidth || 0
    intervalCarousel = setInterval(() => {
      if (currentIndex === data.length - 1) {
        setCurrentIndex(0);
        setTranslateValue(0);
      } else {
        setCurrentIndex(currentIndex + 1);
        setTranslateValue(translateValue - slideWidth);
      }
    }, interval);
  },[currentIndex, translateValue])

  useEffect(() => {
    // 如果自动播放，则直接执行循环
    if(auto){
      handleScroll()
    }
    return () => clearInterval(intervalCarousel);
  }, [auto, currentIndex, translateValue]);

  // 关闭/开启自动播放
  const hanleAutoPlay = useCallback((index:number)=>{
    if(auto){
      // 关闭自动播放功能
      setAutoplay(false)
      setCurrentIndex(index)
      clearInterval(intervalCarousel)
    } else {
       // 开启自动播放功能
      setAutoplay(true)
      setCurrentIndex(index)
    }
    if(index === 0){
      setTranslateValue(0)
    } else {
      const slideWidth: number = (document.querySelector('.carousel') as any)?.clientWidth || 0
      setTranslateValue(-(index*slideWidth));
    }
  },[auto])

  if(data.length === 0){
    return <></>
  }

  return <div 
            className="carousel"
            style={{'--indicators-animation': `${interval}ms` } as CSSProperties}
          >
           <div className="slider-wrapper" style={{ transform: `translateX(${translateValue}px)` }}>
            {
                (data || []).map((item:any, i:number)=><div
                  key={i}
                  className="slider"
                  style={{
                    backgroundImage: `url(${item.serviceUrl})`
                  }}
                  >
                    <div className="text-content" style={{ color: item.color }}>
                      <div className="title">{item.title?.join('\r\n')}</div>
                      <div className="content">{item.desc?.join('\r\n')}</div>
                    </div>
                </div>)
              }
           </div>
            {indicatorDots && <div className="indicators" id="indicators">
              {(data || []).map((_, ind:number) => (
                <span
                  key={ind}
                  className={`${ind === currentIndex ? 'indicator active' : 'indicator'}`}
                  onClick={()=>hanleAutoPlay(ind)}
                >
                  <span className="dot__line">
                    <span className={auto ? "dot__bar__auto": "dot__bar"}/>
                  </span>
                </span>
              ))}
            </div>}
        </div>
}

export default memo(Carousel);
