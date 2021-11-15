import React, { FC } from 'react';

import { CarouselProps } from './index';
import { useSlide, childArray } from './hooks';

interface IProps extends CarouselProps {
  slideData: childArray;
  handleCurrent: (index: number) => void;
}

const Slider: FC<IProps> = (props) => {
  const { slideData, total, current, handleCurrent } = props;
  const [tx] = useSlide(current, total, handleCurrent);

  const trackStyle = {
    transition: `transform 1.2s ease-in`,
    transform: `translateX(${-tx}00%)`,
  };

  return (
    <div className="carousel-list">
      <div className="carousel-track" style={trackStyle}>
        {slideData.length
          ? slideData.map((item, key) => {
              return (
                <div key={key} className="carousel-slide">
                  {item}
                </div>
              );
            })
          : null}
      </div>
    </div>
  );
};

export default Slider;
