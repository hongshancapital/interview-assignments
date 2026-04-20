import clsx from 'clsx';
import { useEffect, useMemo, useRef, useState } from 'react';

export type CarouselItemProps = React.LiHTMLAttributes<HTMLLIElement> &
  Partial<{
    header: React.ReactNode;
    subHeader: React.ReactNode;
  }>;

const CarouselItem: React.FC<CarouselItemProps> = ({
  header,
  subHeader,
  className,
}) => (
  <li
    className={clsx(
      className,
      'carousel-item w-full h-full inline-block align-top py-32'
    )}
  >
    <div className="text-center space-y-3">
      {header && <h1 className="text-5xl font-bold">{header}</h1>}
      {subHeader && <h3 className="text-xl">{subHeader}</h3>}
    </div>
  </li>
);

export type CarouselProps = {
  data: readonly CarouselItemProps[];
  duration?: number;
} & React.HTMLAttributes<HTMLLIElement>;

export const CarouselCSS: React.FC<CarouselProps> = ({
  data,
  duration = 3000,
  className,
}) => {
  const [active, setActive] = useState(0);
  const carouselContainerRef = useRef<HTMLUListElement>(null);
  const slidesElementsRef = useRef<Map<number, HTMLElement | null>>(new Map());
  const processTime = useMemo(() => (duration / 1000).toFixed(2), [duration]);

  useEffect(() => {
    const timer = setInterval(() => {
      setActive((ac) => (ac + 1) % data.length);
    }, duration);
    return () => clearInterval(timer);
  }, [duration, data]);

  useEffect(() => {
    if (carouselContainerRef.current) {
      carouselContainerRef.current.style.transform = `translate3d(-${
        active * 100
      }%, 0, 0)`;
    }

    slidesElementsRef.current.forEach((el, key) => {
      if (!el) {
        return;
      }

      if (key === active) {
        el.style.animation = `${processTime}s scaleX linear`;
      } else {
        el.style.animation = 'none';
      }
    });
  }, [active, processTime]);

  return (
    <div className={clsx(className, 'carousel w-full h-full')}>
      <div className="w-full h-full overflow-hidden relative">
        <ul
          className="carousel-item-container whitespace-nowrap w-full h-full transition-transform linear duration-[.5s]"
          ref={carouselContainerRef}
        >
          {data.map((item, idx) => (
            <CarouselItem key={idx} {...item} />
          ))}
        </ul>
        <ul className="slides-container flex space-x-[5px] justify-center absolute bottom-8 w-full">
          {data.map((_, idx) => (
            <li
              key={idx}
              className="w-[40px] h-[2px] rounded-[1px] bg-[#A9A9A9]"
            >
              <div
                className="slide h-[2px] w-full bg-[#FAFAFA] origin-left transform-gpu scale-x-0"
                ref={(ref) => slidesElementsRef.current.set(idx, ref)}
              />
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};
