import React, { useState, useEffect } from "react";
import "./index.css";

interface ICarouselProps {
  list: IItemProps[];
  duration?: number;
}

interface IItemProps {
  key?: number;
  id?: number;
  title: string;
  description?: string;
  image: string;
  color: string;
}

export const CarouselItem = ({ title, description, image, color }: IItemProps) => {
  return (
    <div className="carousel-item" style={{ backgroundImage: `url(${image})` }}>
      <div className="carousel-info" style={{ color: color }}>
        <h1 className="title">{title}</h1>
        <div className="desc">{description}</div>
      </div>
    </div>
  );
};

const Carousel = ({ list, duration }: ICarouselProps) => {
  const durationTime = duration ?? 3000;
  const AniDurationTime = ((durationTime % 60000) / 1000).toFixed(0);
  const [activeIndex, setActiveIndex] = useState(0);

  const onUpdateIndex = (newIndex: number) => {
    const handle = (index: number) => {
      if (index < 0) {
        index = list.length - 1;
      } else if (index >= list.length) {
        index = 0;
      }
      return index;
    };
    setActiveIndex(newIndex => handle(newIndex + 1));
  };

  const onToggleCarouselIndex = (index: number) => {
    onUpdateIndex(index);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, durationTime);

    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  }, []);

  return (
    <div className="container">
      <div className="inner" style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
        {
          list.map(item => {
            return <CarouselItem key={item.id} { ...item } />
          })
        }
      </div>
      <div className="loading">
        {list?.map((_, index: number) => {
            return (
              <div
                className="indicator-outer"
                onClick={() => onToggleCarouselIndex(index)}
              >
                <div
                  className="indicator-inner"
                  style={{
                    animationDuration: index === activeIndex ? `${AniDurationTime}s` : "0s",
                    backgroundColor: index === activeIndex ? "#FFFFFF" : '',
                  }}
                />
              </div>
            );
          })}
      </div>
    </div>
  );
};

export default Carousel;
