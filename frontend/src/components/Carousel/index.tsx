import React, { useState, useEffect } from 'react';

import './index.scss';

interface IData {
  backImage: string;
  title?: string[];
  titleColor?: string;
  subTitle?: string[];
  subTitleColor?: string;
}

interface IProps {
  data: IData[];
  time?: number;
}

const Carousel = (props: IProps) => {
  const { data, time = 3000 } = props;

  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const setIntervalId = setInterval(() => {
      setCurrentIndex((currentIndex) => { return (currentIndex + 1) % data.length });
    }, time);

    return () => {
      clearInterval(setIntervalId);
    };
  }, [data.length, time, currentIndex]);

  return (
    <div className='carousel-wrap'>
      {data.map(({ backImage, title, titleColor, subTitle, subTitleColor }, index) => {
        return <div
          className={`carousel-item screen-${index === currentIndex ? 0 : index - currentIndex}`}
          key={index}
          style={{
            backgroundImage: `url(${backImage})`,
            color: titleColor
          }}
        >
          {React.Children.map(title, (item) => {
            return <p className='title'>{item}</p>
          })}
          {React.Children.map(subTitle, (item) => {
            return <p className='sub-title' style={{ color: subTitleColor }}>{item}</p>
          })}
        </div>
      })}
      <div className='dots-wrap'>
        {data.map((item, index) => {
          return <div
            key={index}
            style={{ animationDuration: `${time / 1000}s` }}
            onClick={() => setCurrentIndex(index)}
            className={`dots-item ${index === currentIndex ? 'active-dots' : ''}`}
          ></div>
        })}
      </div>
    </div>
  );
}

export default Carousel;