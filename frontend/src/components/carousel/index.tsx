import { useEffect, useState, ReactElement } from 'react';
import { CarouselItem } from './item';
import './index.css'

export const Carousel: React.FC<CarouselProps> = (props: CarouselProps) => {
  const { children, interval = 3 } = props

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

  const slider: number = useSlider(children.length, interval || 3);

  return (
    <div className="carousel">

      {/* 轮播图展示区域 */}
      <div className="carousel-container"
        style={{
          width: `${children.length * 100}%`,
          transform: `translateX(-${100 * slider / children.length}%)`
        }}>
        <CarouselItem {...props}></CarouselItem>
      </div>

      {/* 轮播图进度条 */}
      <div className="carousel-dots" style={{ width: `${children.length * 80}px` }}>
        {
          children && children.length > 0 && children.map((_, i: number) => (
            <i className="dots-item" key={`carousel-dots-${i}`}>
              <i className="dots-bg" style={{ animation: i === slider ? `bg-animation ${interval}s linear` : 'none' }}></i>
            </i>
          ))
        }
      </div>
    </div>
  );
};


export interface CarouselProps {
  children: ReactElement[];
  interval?: number;
}