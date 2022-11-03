import React, { useCallback } from 'react';
import { CarouselInfo } from '../../interface/carousel';
import './index.scss';

function Progress(props: {
  carouselList: Array<CarouselInfo>;
  duration: number;
  currentIndex: number;
}) {
  const { carouselList, duration, currentIndex } = props;

  let style: React.CSSProperties = {
    animation: `progress ${duration / 1000}s linear`,
  };
  return (
    <div className="progress-wrap">
      {carouselList.map((_, progressIndex) => {
        return (
          <div key={progressIndex} className="progress">
            {progressIndex === currentIndex && (
              <div className="progress-active" style={style} />
            )}
          </div>
        );
      })}
    </div>
  );
}

export default Progress;
