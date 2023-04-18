import clsx from 'clsx';
import { useCallback, useEffect, useRef } from 'react';

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

export const Carousel: React.FC<CarouselProps> = ({
  data,
  duration = 3000,
  className,
}) => {
  const carouselContainerRef = useRef<HTMLUListElement>(null);
  const slidesElementsRef = useRef<Map<number, HTMLElement | null>>(new Map());
  const calculateProcess = useCallback(
    (time: number) => Math.round(((time % duration) / duration) * 100) / 100,
    [duration]
  );
  const calculateActive = useCallback(
    (time: number) => Math.floor((time / duration) % data.length),
    [duration, data]
  );

  useEffect(() => {
    let startTime: number;
    let canceled = false;

    const domAnimation = (activeKey: number, process: number) => {
      if (carouselContainerRef.current) {
        carouselContainerRef.current.style.transform = `translate3d(${
          -100 * activeKey
        }%, 0, 0)`;
      }

      slidesElementsRef.current.forEach((el, key) => {
        if (!el) {
          return;
        }

        if (key === activeKey) {
          el.style.transform = `scale3d(${process}, 1, 1)`;
        } else {
          el.style.transform = `scale3d(0, 1, 1)`;
        }
      });
    };

    const animate: FrameRequestCallback = (current) => {
      if (canceled) {
        return;
      }

      if (!startTime) {
        startTime = current;
      }
      const elapsed = current - startTime;
      const activeKey = calculateActive(elapsed);
      const process = calculateProcess(elapsed);

      domAnimation(activeKey, process);

      return window.requestAnimationFrame(animate);
    };

    window.requestAnimationFrame(animate);

    return () => {
      canceled = true;
    };
  }, [calculateActive, calculateProcess]);

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
              className={'w-[40px] h-[2px] rounded-[1px] bg-[#A9A9A9]'}
            >
              <div
                className="slide h-[2px] w-full bg-[#FAFAFA] origin-left"
                ref={(ref) => slidesElementsRef.current.set(idx, ref)}
              />
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};
