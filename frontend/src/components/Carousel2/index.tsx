import React, { useEffect, useState } from "react";
import "./index.css";

interface CarouselItem {
  // 轮播图地址
  url: string;
  // 轮播标题
  title?: string;
  // 标题样式
  titleStyle?: React.CSSProperties;
  // 轮播副标题
  subTitle?: string;
  // 副标题样式
  subTitleStyle?: React.CSSProperties;
}

interface CarouselProps {
  // 轮播项
  data: CarouselItem[];
  // 轮播间隔时间，单位s
  duration?: number;
  // 轮播切换动画完成时间，单位s
  transitionDuration?: number;
}

function Carousel2({
  data,
  duration = 4,
  transitionDuration = 0.3,
}: CarouselProps) {
  const [current, setCurrent] = useState<number>(0);

  useEffect(() => {
    if (data.length > 1) {
      const timer = setTimeout(() => {
        setCurrent(current => (current === data.length - 1 ? 0 : current + 1));
      }, duration * 1000);
      return () => clearTimeout(timer);
    }
  }, [current, data, duration]);

  const translateX = current === 0 ? "0" : `-${100 * current}%`;
  return (
    <div className='carousel2'>
      <div
        className='carousel-items'
        style={{
          transform: `translate3d(${translateX}, 0, 0)`,
          transitionDuration: `${transitionDuration}s`,
        }}
        data-testid='carousel-items'
      >
        {data.map(({ url, title }, index) => {
          return (
            <div key={index} className='carousel-item'>
              <img key={index} src={url} title={title} alt={title} />
            </div>
          );
        })}
      </div>

      {data.length > 0 && (
        <div className='carousel-legends' data-testid='carousel-legends'>
          {data.map((_, index) => {
            const isActive = current === index;
            const transitionDuration = `${duration}s`;
            return (
              <div
                key={index}
                className={`carousel-legend ${isActive ? "active" : ""}`}
              >
                <div
                  className='carousel-legend__inner'
                  style={{
                    transitionDuration: isActive
                      ? transitionDuration
                      : undefined,
                  }}
                ></div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export { Carousel2 };
