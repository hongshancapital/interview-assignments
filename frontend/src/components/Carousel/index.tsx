import CarouselSlide from './components/Slide.tsx';
import Pagination from './components/Pagination.tsx';
import './index.css';
import { useEffect, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface Props {
  count: number;
  delay: number;
  speed: number;
}
type CarouselProps = PropsWithChildren<Props>;

export default function Carousel({
  children,
  count,
  speed,
  delay
}: CarouselProps) {
  const [activeIndex, setActiveIndex] = useState(0);
  const wrapper = useRef<HTMLDivElement>(null);
  const timer = useRef<NodeJS.Timer>();

  function startTimer() {
    timer.current = setTimeout(() => {
      setActiveIndex((index) => (index + 1 > count - 1 ? 0 : index + 1));

      startTimer();
    }, delay);
  }

  useEffect(() => {
    if (wrapper.current) {
      startTimer();
    }

    return () => {
      clearTimeout(timer.current);
    };
  }, [wrapper.current]);

  function slideTo(index: number) {
    setActiveIndex(index);
    clearInterval(timer.current);
    startTimer();
  }

  return (
    <div className="carousel-container">
      <div className="carousel-wrapper" ref={wrapper}
        style={{
          transition:`transform ${speed}ms linear`,
          transform:`translate3d(-${
            100 * activeIndex
          }vw,0,0)
        `}}
      >
        {children}
      </div>
      <Pagination
        slideTo={slideTo}
        pagination={count}
        active={activeIndex}
        delay={delay}
      />
    </div>
  );
}

Carousel.Slide = CarouselSlide;