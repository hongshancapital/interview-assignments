import CarouselSlide from './components/CarouselSlide';
import Pagination from './components/Pagination';
import './index.scss';
import { useEffect, useRef, useCallback, useState, ReactNode } from 'react';

export interface CarouselItem {
  id: number;
  title: ReactNode;
  description: ReactNode;
  className: string;
}

interface CarouselProps {
  list: CarouselItem[];
  delay?: number;
  speed?: number;
}

export default function Carousel({
  list,
  speed = 600,
  delay = 3000,
}: CarouselProps) {
  speed = Math.min(speed, delay);
  const [activeIndex, setActiveIndex] = useState(0);
  const wrapper = useRef<HTMLDivElement>(null);
  const timer = useRef<NodeJS.Timer>();
  const len = list.length;

  const startTimer = useCallback(() => {
    timer.current = setTimeout(() => {
      setActiveIndex((index) => (index + 1) % len);

      startTimer();
    }, delay);
  }, [delay, len]);

  const onSlideTo = useCallback(
    (index: number) => {
      setActiveIndex(index);
      clearTimeout(timer.current);
      startTimer();
    },
    [startTimer],
  );

  useEffect(() => {
    if (wrapper.current) {
      startTimer();
    }

    return () => {
      clearTimeout(timer.current);
    };
  }, [startTimer]);

  return (
    <div className="carousel-container">
      <div
        className="carousel-wrapper"
        ref={wrapper}
        style={{
          width: `${len * 100}vw`,
          transition: `transform ${speed}ms ease-in-out`,
          transform: `translate3d(-${100 * activeIndex}vw,0,0)
        `,
        }}
      >
        {list.map((el, slideIndex) => (
          <Carousel.Slide
            className={`${
              slideIndex === activeIndex ? 'carousel-slide-active' : ''
            } ${el.className || ''}`}
            key={el.id}
          >
            <h2 className="title">{el.title}</h2>
            <p className="text">{el.description}</p>
          </Carousel.Slide>
        ))}
      </div>
      <Pagination
        list={list}
        activeIndex={activeIndex}
        delay={delay}
        onSlideTo={onSlideTo}
      />
    </div>
  );
}

Carousel.Slide = CarouselSlide;
