import React, { useState, useEffect } from 'react';
import { Card, ICardData } from './Card';
import Timeline from './Timeline';
import './index.css';

export interface ICarouselProps {
  data: ICardData[]; // 幻灯片数据列表
  stayTime?: number; // 幻灯片停留的时长（s）
  duration?: number; // 幻灯片切换的动画时长（s）
}

/**
 * 幻灯片组件
 */
const Carousel: React.FC<ICarouselProps> = ({
  data,
  stayTime = 3.2,
  duration = 0.6,
}) => {
  const count = data.length;
  // 当前是展示幻灯片的序号值
  const [currentIndex, setCurrentIndex] = useState(0);

  // 间隔执行幻灯片动画
  useEffect(() => {
    const si = setInterval(() => {
      setCurrentIndex(currentIndex >= count - 1 ? 0 : currentIndex + 1);
    }, stayTime * 1000);
    return () => {
      clearTimeout(si);
    };
  }, [count, currentIndex, duration, stayTime]);

  return (
    <div className="carousel-wrapper">
      {/* 幻灯片部分 */}
      <div
        className="card-wrapper"
        style={{
          transition: `${duration}s`,
          transform: `translateX(${-100 * currentIndex}%)`,
        }}
      >
        {data.map((card, i) => (
          <Card key={`card${i}`} data={card} />
        ))}
      </div>
      {/* 时间线部分 */}
      <div className="timeline-wrapper">
        {data.map((_card, i) => (
          <Timeline
            key={`timeline${i}`}
            isActive={i === currentIndex}
            duration={stayTime}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
