import React, { useState, useEffect } from 'react';
import './index.css'

interface CarouselProps {
  data?: { bgColor?: string, image: string, text: React.ReactNode }[]; // 轮播数据
  interval?: number; // 轮播间隔，默认3s
}

const Carousel1: React.FC<CarouselProps> = ({ data = [], interval = 3 }) => {
  // 当前展示的轮播图索引
  const [currentIndex, setCurrentIndex] = useState(0);
  const [translateX, setTranslateX] = useState(0);

  // 改变当前索引及动画效果
  const slideFn = (idx: number) => {
    setCurrentIndex(idx);
    setTranslateX(-(idx) * 100);
  };

  useEffect(() => {
    // 下个轮播图索引
    const nextIndex = (currentIndex + 1) % data.length;
    // 启用计时器
    const timeout = setTimeout(() => {
      slideFn(nextIndex);
    }, interval * 1000);
    // 清除计时器
    return () => {
      clearTimeout(timeout);
    };
  }, [currentIndex, data.length, interval]);

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel"
        style={{ transform: `translateX(${translateX}vw)`, transition: 'transform 0.5s' }}
      >
        {
          data.map((item, index) => (
            <div className="carousel-item" key={index} style={{ backgroundColor: item.bgColor}}>
              <div className='carousel-item-text'>{item.text}</div>
              <img src={item.image} alt={`img-${index}`} />
            </div>
          ))
        }
      </div>
      <div className="carousel-lineBox">
        {
          data.map((item, index) => (
            <div className="carousel-line" key={index} onClick={() => slideFn(index)}>
              <div className='carousel-line-active' style={{ width: currentIndex === index ? '100%' : 0, transition: currentIndex === index ? `all ${interval}s linear`: 'none'}}></div>
            </div>
          ))
        }
      </div>
    </div>
  );
};

export default Carousel1;