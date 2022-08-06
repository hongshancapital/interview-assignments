import React, { useEffect, useCallback, useState } from 'react';
import { CarouselInfo } from '../../interface/carousel';
import CarouselItem from '../carouselItem/index';
import Progress from '../progress/index';
import './index.scss';

export default function Carousel(props: {
  carouselList: Array<CarouselInfo>;
  duration: number;
}) {
  const { carouselList, duration } = props;
  const carouselLength = carouselList.length;
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      if (currentIndex >= carouselLength - 1) {
        setCurrentIndex(0);
      } else {
        setCurrentIndex(currentIndex + 1);
      }
    }, duration);
    return () => clearInterval(timer);
  }, [carouselLength, duration, currentIndex]);

  return (
    <div className="carousel-wrap">
      <div
        className="carousel-list"
        style={{ transform: `translateX(-${currentIndex * 100}%)` }}
      >
        {carouselList.map((item: CarouselInfo) => {
          return <CarouselItem carouselInfo={item} key={item.title[0]} />;
        })}
      </div>
      <Progress
        carouselList={carouselList}
        duration={duration}
        currentIndex={currentIndex}
      />
    </div>
  );
}
