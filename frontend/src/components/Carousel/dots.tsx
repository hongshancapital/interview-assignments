import React, { FC, memo } from 'react';

import { CarouselProps } from './index';

interface IProps extends CarouselProps {
  speed?: number;
}

const Dots: FC<IProps> = ({ total, current, speed }) => {
  const array = new Array(total).fill(' ');

  return (
    <div className="carousel-dots">
      {array.length
        ? array.map((value, key) => (
            <div key={key} className="carousel-dot">
              {key + 1 === current && (
                <i
                  className="carousel-fill"
                  style={{ animationDuration: `${speed}s` }}
                ></i>
              )}
            </div>
          ))
        : null}
    </div>
  );
};

export default memo(Dots);
