import React, { FC, useCallback, useMemo } from 'react';
import { IDotsProps } from '../../interface/dots';
import './index.scss';

const Dots: FC<IDotsProps> = props => {
  const { count, activeIndex, delay } = props;

  const isActive = useCallback(index => index === activeIndex, [activeIndex]);

  const dotsList = useMemo(() => {
    return Array.from({ length: count }, (item, index) => index);
  }, [count]);

  return (
    <div className="dot__container">
      {dotsList.map((currentIndex, index) => (
        <div className="dot__item" key={currentIndex}>
          <div
            className={`dot__mask  ${isActive(currentIndex) ? 'dot__mask--active' : ''}`}
            style={isActive(currentIndex) ? { animationDuration: `${delay}s` } : {}}
          />
        </div>
      ))}
    </div>
  );
};

export default Dots;
