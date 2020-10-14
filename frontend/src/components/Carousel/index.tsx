import React, { useState, useEffect } from 'react';

import './css/index.css';

export interface SliderType {
  title: string[],
  desc?: string[],
  color?: string,
  bgc?: string,
  bgi?: any;
}

export type DataSourceType = SliderType[];

export interface CarouselType {
  dataSource: DataSourceType,
  duration: number
}

function Carousel(props: CarouselType) {
  const { duration, dataSource } = props;
  const { length } = dataSource;

  const [slideIndex, setSlideIndex] = useState(0);
  const [sliding, setSliding] = useState(false);

  useEffect(() => {
    setSliding(true);

    let timer: any = setInterval(() => {
      setSlideIndex((slideIndex + 1) % length);
    }, duration);

    return () => {
      if (timer) {
        clearInterval(timer);
        timer = null;
      }
    };
  }, [slideIndex, length, duration, sliding]);

  return (
    <div className='swiper-container'>
      <div
        className='swiper-wrapper'
        style={{
          width: length * 100 + 'vw',
          transform: `translateX(${-100 * slideIndex}vw)`,
        }}
      >
        {dataSource.map((value, index) => {
          return (
            <div
              key={'slider' + index}
              className='swiper-slider'
              style={{
                backgroundColor: value.bgc,
                color: value.color,
              }}
            >
              <div className='text-wrapper'>
                {
                  value.title.map((v, i) => {
                    if (value.title.length === 1 && i === 0) {
                      return (
                        <div
                          key={'text-title' + i}
                          className='title pb20'
                        >
                          {v}
                        </div>
                      );
                    }
                    return (
                      <div
                        className='text-title'
                        key={'title' + i}
                      >
                        {v}
                      </div>
                    );
                  })
                }
                {
                  value.desc && value.desc.map((v, i) => (
                    <div
                      className='text-desc'
                      key={'desc' + i}
                    >
                      {v}
                    </div>
                  ))
                }
              </div>
              <div className='swiper-img-wrapper'>
                {
                  value.bgi &&
                  <img
                    src={value.bgi}
                    alt=''
                  />
                }
              </div>
            </div>
          );
        })}
      </div>
      <div
        className='swiper-bar'
      >
        {dataSource.map((value, index): any => {
          let swiperBarItemSlotClassname = 'swiper-bar-item-slot';

          if (slideIndex === index && sliding) {
            swiperBarItemSlotClassname += ' swiper-bar-item-slot-transition';
          }

          return (
            <div
              key={'swiper-bar' + index}
              className='swiper-bar-item'
            >
              <div
                className={swiperBarItemSlotClassname}
              >
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Carousel;
