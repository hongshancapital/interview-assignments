import React from 'react';
import classNames from 'classnames';
import { ICarouselItem } from '../../constants/carouselList';
import './Carousel.css';

export interface CarouselProps {
  images: Array<ICarouselItem>; // slide列表信息
  interval?: number; // 切换间隔时长，默认5s切换一次
  transitionDuration?: number; // 过渡间隔时长，默认1s过渡时间
}

export function Carousel({
  images,
  interval = 3000,
  transitionDuration = 1000,
}: CarouselProps) {
  const [activeIndex, setActiveIndex] = React.useState(0);

  React.useEffect(() => {
    const timeout = setTimeout(() => {
      setActiveIndex((activeIndex + 1) % 3);
    }, interval);

    return () => clearTimeout(timeout);
  }, [activeIndex, interval]);

  const [initialized, setInitialized] = React.useState(false);
  React.useEffect(() => {
    setInitialized(true);
  }, []);

  return (
    <div className="Carousel">
      <div
        className="CarouselGallery"
        style={{
          width: `${images.length * 100}%`,
          transform: `translate(-${(activeIndex * 100) / images.length}%, 0)`,
          transition: `transform ${transitionDuration / 1000}s`,
        }}
      >
        {images.map(
          (
            { title, subTitle, backgroundColor, fontColor, imageStyle },
            index
          ) => (
            <div
              key={index}
              style={{
                backgroundColor,
                width: `${100 / images.length}%`,
              }}
              className="CarouselImageItem"
            >
              <div className="CarouselTitle" style={{ color: fontColor }}>
                {title}
              </div>
              <div className="CarouselSubTitle" style={{ color: fontColor }}>
                {subTitle}
              </div>
              <div className="CarouselImage" style={imageStyle} />
            </div>
          )
        )}
      </div>
      <div className="CarouselIndexContainer">
        {images.map((_, index) => (
          <div
            key={index}
            className={classNames(
              'CarouselIndexItem',
              activeIndex === index ? 'active' : ''
            )}
          >
            <div
              className={classNames(
                'CarouselIndexItemAniPart',
                initialized && activeIndex === index ? 'active' : ''
              )}
              style={{
                transition: `left ${interval / 1000}s linear`,
              }}
            />
          </div>
        ))}
      </div>
    </div>
  );
}
