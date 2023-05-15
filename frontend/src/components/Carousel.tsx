import clsx from 'clsx';
import React, {
  createContext,
  useState,
  useEffect,
  useContext,
  ButtonHTMLAttributes,
} from 'react';
import { useInterval } from '../useUtils';

/**
 * Headless Carousel component by IoC and Render Props pattern
 * - html structure and styling is up to the user
 *
 * What is Headless UI? - https://headlessui.dev/ https://www.radix-ui.com/
 * What is Inversion of Control? - https://kentcdodds.com/blog/inversion-of-control
 *
 * FIXME:
 * Myself is not using React in daily works, some knowledge may be out of date.
 * maybe render props can be replaced by hooks here, might require user manual to call hook, idk.
 * - pros: no need to pass props down to children, jsx is cleaner
 * - cons: user need to call hook manually
 */

/**
 * TODO: add setSlideByIndex method allowing users to set the slide by index
 */
interface CarouselContextProps {
  currentIndex: number;
  setSlideCount: React.Dispatch<React.SetStateAction<number>>;
  slideCount: number;
  interval: number;
}

export const CarouselContext = createContext<CarouselContextProps>({
  currentIndex: 0,
  slideCount: 0,
  interval: 0,
  setSlideCount: () => {},
});

export const CarouselProvider: React.FC<{
  interval?: number;
  children: (props: CarouselContextProps) => React.ReactNode;
}> = ({ interval = 3000, children }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [slideCount, setSlideCount] = useState(0);

  const nextSlide = () => {
    setCurrentIndex((prevIndex) => prevIndex + 1);
  };

  useInterval(nextSlide, interval);
  useEffect(() => {
    // Reset to the first slide when currentIndex is out of bounds
    if (currentIndex < 0 || currentIndex > slideCount - 1) {
      setCurrentIndex(0);
    }
  }, [currentIndex, children, slideCount]);

  return (
    <CarouselContext.Provider
      value={{ interval, slideCount, setSlideCount, currentIndex }}
    >
      {children({
        interval,
        slideCount,
        setSlideCount,
        currentIndex,
      })}
    </CarouselContext.Provider>
  );
};

export const Carousel: React.FC<{
  className?: string;
  interval?: number;
  children: (props: CarouselContextProps) => React.ReactNode;
}> = ({ className, interval, children, ...props }) => {
  return (
    <CarouselProvider interval={interval}>
      {(ctx) => {
        return (
          <div
            role='region'
            className={clsx('carousel', className)}
            aria-roledescription='carousel'
            aria-live='polite'
            {...props}
          >
            {children(ctx)}
          </div>
        );
      }}
    </CarouselProvider>
  );
};

export const CarouselSlide: React.FC<{
  activeIndex: number;
  children: React.ReactNode;
  className?: string;
}> = ({ activeIndex, children, className, ...props }) => {
  const ctx = useContext(CarouselContext);

  useEffect(() => {
    ctx.setSlideCount((prevCount: number) => prevCount + 1);
    return () => {
      ctx.setSlideCount((prevCount: number) => prevCount - 1);
    };
  }, []);

  const isActive = ctx.currentIndex === activeIndex;
  return (
    <div
      className={clsx('carousel-item', className)}
      role='group'
      aria-roledescription='slide'
      aria-label={`${activeIndex + 1} of ${ctx.slideCount} slides`}
      tabIndex={isActive ? 0 : -1}
      {...props}
    >
      {children}
    </div>
  );
};

export const CarouselIndicatorList: React.FC<{
  className?: string;
  style?: React.CSSProperties;
  children: (
    indicators: {
      isActive: boolean;
      props: ButtonHTMLAttributes<HTMLButtonElement>;
    }[],
  ) => React.ReactNode;
}> = ({ style, className, children }) => {
  const ctx = useContext(CarouselContext);
  const indicators = '*'
    .repeat(ctx.slideCount)
    .split('')
    .map((_, index) => {
      const isActive = ctx.currentIndex === index;
      return {
        isActive,
        props: {
          role: 'tab',
          'aria-label': `${index + 1} of ${ctx.slideCount}`,
          'aria-selected': isActive,
        },
      };
    });
  return (
    <div
      style={style}
      className={clsx('carousel-indicators', className)}
      role='tablist'
    >
      {children(indicators)}
    </div>
  );
};
