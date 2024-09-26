import React, { FC, useCallback, useState } from 'react';
import './styles.scss';

export type CarouselContents = {
  poster: string;
  heading: React.ReactNode;
  subHeading?: React.ReactNode;
  /**
   * The background color for slide
   */
  backgroundColor: string;
  /**
   * Font color
   */
  color: string;
};

type CarouselProps = {
  /**
   * Time (s) interval of slide switching
   */
  interval?: number;
  /**
   * Time (s) duration of slide transition
   */
  duration?: number;
  contents: CarouselContents[];
};

const Carousel: FC<CarouselProps> = ({
  contents,
  interval = 3,
  duration = 1,
}) => {
  const [activedIndex, setActivedIndex] = useState<number>(0);

  const handleAnimationEnd = useCallback(() => {
    // Calculate the index for the next rotating slide. If the current index is the last one then jump to the 0 position.
    setActivedIndex((activedIndex + 1) % contents.length);
  }, [activedIndex, contents.length]);

  return (
    <div className='carousel'>
      <div
        className='slider'
        style={{
          transform: `translateX(-${activedIndex * 100}%)`,
          transition: `${duration}s`,
        }}>
        {contents.map(
          ({ heading, subHeading, poster, color, backgroundColor }) => {
            return (
              <div
                className='slide'
                key={poster}
                style={{ backgroundColor, color }}>
                {heading}
                {subHeading}
                <div
                  className='poster'
                  style={{ backgroundImage: `url(${poster})` }}></div>
              </div>
            );
          }
        )}
      </div>
      <div className='dot-container'>
        {contents.map(({ poster }, index) => {
          return (
            <div className='dot-box' key={poster}>
              <div
                data-testid={`animation-dot-${index}`}
                onAnimationEnd={handleAnimationEnd}
                style={{ animationDuration: `${interval}s` }}
                className={`dot ${
                  activedIndex === index ? 'actived' : ''
                }`}></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
