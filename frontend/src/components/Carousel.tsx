import './styles.css';
import React, { useState, useEffect } from 'react';
import { IMG_SIZE_MAP } from '../constants/carousel';
import { CarouselProps } from './carousel.types';

/**
 * author: 郑文盛 Vince
 * mobile/WeChat 18602756091
 */

/**
 * 一个自动切换的简易轮播图组件
 * props
 * @param {array} props.slides 轮播图内容数组，可动态配置轮播图每页的内容，包含标题文字、内容文字、背景色、文字颜色、图片文件名等字段
 * @param {number} props.interval (可选)配置每页停留时间
 */
const Carousel: React.FC<CarouselProps> = (props) => {
  const { slides, interval = 3000 } = props;
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const timerId = setInterval(() => {
      setCurrentIndex((prev) => {
        if (prev === slides.length - 1) {
          return 0;
        } else {
          return prev + 1;
        }
      });
    }, interval);

    return () => {
      clearInterval(timerId);
    };
    // eslint-disable-next-line
  }, []);

  return (
    <div className="carousel">
      <div className="carousel-indicators">
        {slides.map((_, index) => (
          <div
            className={
              index === currentIndex
                ? 'carousel-indicator-active'
                : 'carousel-indicator'
            }
            key={index}
          ></div>
        ))}
      </div>
      <div
        className="carousel-content"
        style={{ transform: `translateX(-${currentIndex * 100}%)` }}
      >
        {slides.map((slide, index) => (
          <div
            className="carousel-item"
            key={index}
            style={{ background: slide.bgColor }}
          >
            <div
              className="item-title"
              style={{ color: slide.textColor || undefined }}
            >
              {slide.title}
            </div>
            <div
              className="item-text"
              style={{ color: slide.textColor || undefined }}
            >
              {slide.text}
            </div>
            <img
              src={require(`../assets/${slide.img}`)}
              alt=""
              className={IMG_SIZE_MAP[slide.imgSize]}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
