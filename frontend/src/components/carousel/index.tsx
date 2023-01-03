import React, { useEffect, useState, ReactElement, useImperativeHandle } from 'react';
import { CarouselItems } from './items';
import './index.css'

export const Carousel = React.forwardRef<any, CarouselProps>((props, ref) => {
  const { children = [], interval = 3 } = props

  useImperativeHandle(ref, () => ({
    ...ref,
    slider: slider
  }))


  const useInterval = (callback: (time: number) => void, interval: number) => {
    useEffect(() => {
      const start = new Date().valueOf();

      const I = setInterval(() => {
        callback(new Date().valueOf() - start)
      }, interval)

      return () => clearInterval(I)
    }, [])
  }

  const useSlider = (N: number, speed = 3) => {
    const [slider, setSlider] = useState<number>(0)

    useInterval((diff) => {
      setSlider(Math.floor(diff / (speed * 1000)) % N)
    }, 300)

    return slider
  }

  const slider: number = useSlider(children?.length, interval || 3);

  return (
    <div className="carousel" data-testid="carousel" ref={ref}>

      {/* 轮播图展示区域 */}
      <div className="carousel-container"
        style={{
          width: `${children.length * 100}%`,
          transform: `translateX(-${100 * slider / children.length}%)`
        }}>
        <CarouselItems {...props}></CarouselItems>
      </div>

      {/* 轮播图进度条 */}
      <div className="carousel-dots" style={{ width: `${children.length * 80}px` }}>
        {
          children && children.length > 0 && children.map((_, i: number) => (
            <i className="dots-item" key={`carousel-dots-${i}`}>
              <i
                className="dots-bg"
                data-testid="dots-bg"
                style={{ animation: i === slider ? `bg-animation ${interval}s linear` : 'none' }}
              ></i>
            </i>
          ))
        }
      </div>
    </div>
  );
});


export interface CarouselProps {
  children?: ReactElement[];
  interval?: number;
}