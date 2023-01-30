import { useState, useEffect, useRef } from 'react';

export interface IUseCarouselProps {
  /**
   * 总长度
   */
  length: number;

  /**
   * 持续时间(ms)
   */
  duration?: number;

  /**
   * 默认展示的index值
   */
  defaultActiveIndex?: number;
}

const useCarousel = ({
  length,
  duration = 3000,
  defaultActiveIndex = 0,
}: IUseCarouselProps) => {
  const [activeIndex, setActiveIndex] = useState<number>(defaultActiveIndex);
  const timer = useRef<NodeJS.Timer>();

  useEffect(() => {
    let index = defaultActiveIndex;
    timer.current = setInterval(() => {
      if (index === length - 1) {
        index = 0;
        setActiveIndex(index);
      } else {
        index = index + 1;
        setActiveIndex(index);
      }
    }, duration || 3000);

    return () => {
      if (timer.current) {
        clearInterval(timer.current);
      }
    };
  }, [length, duration, defaultActiveIndex]);

  return activeIndex;
};

export default useCarousel;
