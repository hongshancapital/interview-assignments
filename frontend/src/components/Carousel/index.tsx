import React, { useEffect, useState } from "react";
import "./index.css";

export const CAROUSEL_DURATION_DEFAULT = 4;
export const CAROUSEL_TRANSITION_DURATION_DEFAULT = 0.3;

export interface CarouselItem {
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
  // 轮播间隔时间，单位s，默认值4
  duration?: number;
  // 轮播切换动画完成时间，单位s，默认值0.3
  transitionDuration?: number;
}

function Carousel({
  data,
  duration = CAROUSEL_DURATION_DEFAULT,
  transitionDuration = CAROUSEL_TRANSITION_DURATION_DEFAULT,
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
    <div className='carousel'>
      <div
        className='carousel-items'
        style={{
          transform: `translate3d(${translateX}, 0, 0)`,
          transitionDuration: `${transitionDuration}s`,
        }}
        data-testid='carousel-items'
      >
        {data.map(({ url, title, subTitle, titleStyle, subTitleStyle }, index) => {
          // 处理换行数据
          const titles = title?.split("\n").filter(v => v.trim()) || [];
          const subTitles = subTitle?.split("\n").filter(v => v.trim()) || [];
          return (
            <div
              key={index}
              title={title}
              className='carousel-item'
              style={{ backgroundImage: `url(${url})` }}
            >
              <div className='carousel-item__info'>
                <p className='carousel-item__title' style={titleStyle}>
                  {titles.map((t, index) => (
                    <span key={index}>
                      {t}
                      {index !== titles.length - 1 && <br />}
                    </span>
                  ))}
                </p>
                <p className='carousel-item__subtitle' style={subTitleStyle}>
                  {subTitles.map((t, index) => (
                    <span key={index}>
                      {t}
                      {index !== subTitles.length - 1 && <br />}
                    </span>
                  ))}
                </p>
              </div>
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
              <div key={index} className={`carousel-legend ${isActive ? "active" : ""}`}>
                <div
                  className='carousel-legend__inner'
                  style={{
                    transitionDuration: isActive ? transitionDuration : undefined,
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

export { Carousel };
