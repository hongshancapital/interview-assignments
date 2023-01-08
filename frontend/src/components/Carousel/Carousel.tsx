import React, { FC, useEffect, useState } from 'react';
import './styles.scss';
import Dots from './Dots';

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
   * Time (s) interval of switching
   */
  interval?: number;
  /**
   * Time (s) duration of transition
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

  useEffect(() => {
    const intervalId = setInterval(() => {
      setActivedIndex((activedIndex + 1) % contents.length);
    }, interval * 1000);
    return () => clearInterval(intervalId);
  }, [activedIndex, contents.length, interval]);

  return (
    <div className='carousel'>
      <div
        className='slider'
        style={{
          transform: `translateX(-${activedIndex * 100}%)`,
          transition: `${duration}s`,
        }}>
        {contents?.map(
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
      <Dots
        dotNumber={contents.length}
        activedIndex={activedIndex}
        animationDuration={interval}
      />
    </div>
  );
};

export default Carousel;
