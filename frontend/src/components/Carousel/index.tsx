import { useEffect, useLayoutEffect, useRef, useState } from 'react';
import styles from './index.module.scss';

const SLIDER_WIDTH = 40;

/**
 *
 * @param interval - The interval(ms) of switching pages. Default to 1000
 * @example
 * ```
 * <Carousel interval={2000}>
 *  <div>page1</div>
 *  <div>page2</div>
 *  <div>page3</div>
 * </Carousel>
 * ```
 *
 */
export default function Carousel({
  children,
  interval = 1000,
}: {
  children: JSX.Element | JSX.Element[];
  interval?: number;
}): JSX.Element {
  const [curPage, setCurPage] = useState(0);

  const containerRef = useRef<HTMLDivElement>(null);
  const scrollContainerRef = useRef<HTMLDivElement>(null);
  const pageRefArr = useRef<HTMLDivElement[]>([]);
  const sliderProgressRefArr = useRef<HTMLDivElement[]>([]);

  const renderedChildren = Array.isArray(children) ? children : [children];
  const totalPages = renderedChildren.length;

  useLayoutEffect(() => {
    if (scrollContainerRef.current && containerRef.current) {
      const containerWidth = containerRef.current.offsetWidth;

      // set width of scrollContainer
      scrollContainerRef.current.style.width = `${
        totalPages * containerWidth
      }px`;

      // set width of eveyr page
      for (const page of pageRefArr.current) {
        page.style.width = `${containerWidth}px`;
      }
    }
  }, [totalPages, interval]);

  // start interval timer to update current page
  useEffect(() => {
    const timer = setInterval(() => {
      setCurPage((prev) => {
        return (prev + 1) % totalPages;
      });
    }, interval);

    return () => {
      clearInterval(timer);
    };
  }, [interval, totalPages]);

  // when current page updated, transform scrollContainer, and play the slider progress
  useEffect(() => {
    if (scrollContainerRef.current && containerRef.current) {
      const width = containerRef.current.offsetWidth;

      scrollContainerRef.current.style.transform = `translateX(${
        -width * curPage
      }px)`;

      sliderProgressRefArr.current.forEach((sliderProgress, index) => {
        if (index === curPage) {
          sliderProgress.style.transition = `width ${interval}ms linear`;
          sliderProgress.style.width = `${SLIDER_WIDTH}px`;
        } else {
          sliderProgress.style.transition = '';
          sliderProgress.style.width = '0';
        }
      });
    }
  }, [curPage, interval]);

  return (
    <div className={styles.container} ref={containerRef}>
      <div
        className={styles.scrollContainer}
        ref={scrollContainerRef}
        data-testid="carousel-scroll-container"
      >
        {renderedChildren.map((page, index) => {
          return (
            <div
              key={index}
              className={styles.page}
              ref={(ref) => {
                pageRefArr.current[index] = ref as HTMLDivElement;
              }}
            >
              {page}
            </div>
          );
        })}
      </div>

      <div className={styles.sliderContainer}>
        {renderedChildren.map((_, index) => {
          return (
            <div
              key={index}
              className={styles.slider}
              style={{ width: SLIDER_WIDTH }}
            >
              <div
                className={styles.sliderProgress}
                data-testid="carousel-slider-progress"
                ref={(ref) => {
                  sliderProgressRefArr.current[index] = ref as HTMLDivElement;
                }}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
