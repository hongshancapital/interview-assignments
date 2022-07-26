import React, { useEffect, useMemo, useState, FC } from "react";
import Indicators from "./Indicators";
import './style/index.scss';
export interface CarouselProps {
  height?: string;
  duration?: number;
  animationDuration?: number;
  initActiveIndex?: number;
  children: React.ReactNode;
}

const Carousel: FC<CarouselProps> = (props: CarouselProps) => {

  const { initActiveIndex = 0, animationDuration = 500, duration = 3000, height = '100%' } = props;

  const [activeIndex, setActiveIndex] = useState(initActiveIndex);

  const childrenCount = useMemo(() => React.Children.count(props.children), [
    props.children
  ]);

  useEffect(() => {
    const timer = setInterval(() => {
      setActiveIndex(currentIndex => (currentIndex + 1) % (childrenCount || 1));
    }, duration);
    return () => {
      clearInterval(timer);
    }
  }, [childrenCount, duration]);

  return (
    <div className="carousel__container" style={{ height: height }}>
      <div className="carousel__wrap" style={{
        width: `${childrenCount * 100}% `,
        transition: `all linear ${animationDuration}ms`,
        transform: `translateX(-${activeIndex * (100 / childrenCount)}%)`
      }}>
        {React.Children.map(props.children, (child) => {
          if (!React.isValidElement(child)) return null;
          return <div className="carousel__item">
                    {child}
                </div>;
        })}
      </div>
      <Indicators activeIndex={activeIndex} animationDuration={duration} count={childrenCount}></Indicators>
    </div>
  );
}

export default Carousel;