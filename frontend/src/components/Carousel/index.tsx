import React, { useState, useEffect } from 'react';

import airpods from '../../assets/airpods.png';
import iphone from '../../assets/iphone.png';
import tablet from '../../assets/tablet.png';

import './index.css';

interface Sliders {
  title: string[],
  desc: string[],
  color?: string,
  bgc?: string,
  bgi?: any;
}

const sliders: Sliders[] = [
  {
    title: ['xPhone'],
    desc: ['Lots to love.Less to spend.', 'Starting at $399.'],
    color: '#fff',
    bgc: '#111111',
    bgi: iphone,
  },
  {
    title: ['Tablet'],
    desc: ['Just the right amount of everything'],
    bgc: '#FAFAFA',
    bgi: tablet,
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    desc: [],
    bgc: '#F1F1F3',
    bgi: airpods,
  },
];

const duration = 3000;

function Carousel() {
  const [slideIndex, setSlideIndex] = useState(0);

  useEffect(() => {
    let timer: any = setInterval(() => {
      setSlideIndex((slideIndex + 1) % sliders.length);
    }, duration);

    return () => {
      if (timer) {
        clearInterval(timer);
        timer = null;
      }
    };
  }, [slideIndex]);

  return (
    <div className='swiper'>
      <div
        className='swiper-wrapper'
        style={{
          width: sliders.length * 100 + 'vw',
          transform: `translateX(${-100 * slideIndex}vw)`,
        }}
      >
        {
          sliders.map((value, index) => {
            return (
              <div
                key={'slider' + index}
                className='swiper-slider'
                style={{
                  backgroundColor: value.bgc,
                  color: value.color,
                }}
              >
                <div className='text'>
                  {
                    value.title.map((v, i) => {
                      if (value.title.length === 1 && i === 0) {
                        return (
                          <div
                            key={'title' + i}
                            className='title'
                            style={{
                              paddingBottom: 20,
                            }}
                          >
                            {v}
                          </div>
                        );
                      }
                      return (
                        <div
                          className='title'
                          key={'title' + i}
                        >
                          {v}
                        </div>
                      );
                    })
                  }
                  {
                    value.desc.map((v, i) => (
                      <div
                        className='desc'
                        key={'desc' + i}
                      >
                        {v}
                      </div>
                    ))
                  }
                </div>
                <div className='bgi'>
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
          })
        }
      </div>
      <div
        className='swiper-bar'
        style={{
          backgroundColor: 'rgba(0,0,0,0)',
          color: sliders[slideIndex].color ? sliders[slideIndex].color : '#000',
        }}
      >
        <div className='swiper-bar-wrapper'>
          {sliders.map((value, index, array): any => {
            /*
            * 页面在初次渲染时，存在问题
            * 首次渲染时，第一个进度条无法自动读条，必须要等第二轮才能自动读条
            * */

            const swiperBarItemSlotStyles: any = {
              width: '0%',
            };

            if (slideIndex === index) {
              swiperBarItemSlotStyles.transition = `all ${duration}ms linear`;
              swiperBarItemSlotStyles.width = '100%';
            }

            return (
              <div
                key={'swiper-bar' + index}
                className='swiper-bar-item'
                style={{
                  backgroundColor: '#ccc',
                }}
              >
                <div
                  className='swiper-bar-item-slot'
                  style={{
                    backgroundColor: '#888',
                    width:'0%',
                    ...swiperBarItemSlotStyles,
                  }}
                >

                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default Carousel;
