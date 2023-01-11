import './styles.css';
import React, { useState, useEffect } from 'react';

type SlideType = {
  bgColor: string,
  textColor?: string,
  title: string,
  text: string,
  img: string
}

interface CarouselProps {
  slides: SlideType[];
  interval?: number;
}

const Carousel: React.FC<CarouselProps> = (props) => {
  const { slides, interval = 3000 } = props;
  const [currentIndex, setCurrentIndex] = useState<number>(0);

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
              className="item-img"
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
