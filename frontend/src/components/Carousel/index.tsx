import {
  memo,
  FC,
  useState,
  useEffect,
  useRef,
  useCallback,
  Children,
  ReactNode,
  useMemo,
} from 'react';
import SlickDots from './SlickDots';
import './index.css';
interface CarouselProps {
  /** carousel classname */
  className?: string;
  /** panel switching speed */
  speed?: number;
  /** dwell time per panel */
  duration?: number;
  /** whether to switch panels automatically */
  autoPlay?: boolean;
  /** panel height */
  height?: string;
  /** collection of panels */
  children?: ReactNode;
}
const Carousel: FC<CarouselProps> = function ({
  className = '',
  speed = 1,
  duration = 3,
  autoPlay = true,
  height,
  children,
}) {
  const length = Children.count(children);
  const timeoutRef = useRef<NodeJS.Timer>();
  const [activeIndex, setActiveIndex] = useState(0);

  const handleAutoPlay = useCallback(() => {
    if (!autoPlay) return;
    clearInterval(timeoutRef.current);
    timeoutRef.current = setInterval(() => {
      setActiveIndex((pre) => {
        if (pre < length - 1) return pre + 1;
        return 0;
      });
    }, duration * 1000);
  }, [length, autoPlay, duration]);

  useEffect(() => {
    handleAutoPlay();
    return () => {
      clearInterval(timeoutRef.current);
    };
  }, [handleAutoPlay]);

  const handleClickDot = (index: number) => {
    if (index === activeIndex) return;
    setActiveIndex(index);
    handleAutoPlay();
  };

  const slickListDuration = useMemo(() => {
    return Math.min(speed, duration);
  }, [speed, duration]);

  return (
    <div className={`carousel ${className}`} style={{ height }}>
      <ul
        className="slick-list"
        style={{
          transform: `translateX(-${activeIndex * 100}%)`,
          transitionDuration: `${slickListDuration}s`,
        }}
      >
        {Children.map(children, (child, index) => {
          return <li key={index}>{child}</li>;
        })}
      </ul>
      <SlickDots
        length={length}
        activeIndex={activeIndex}
        duration={duration}
        onClick={handleClickDot}
      />
    </div>
  );
};
export default memo(Carousel);
