import React, { FC } from 'react';

import { ProgressBar } from './ProgressBar';

export interface IndicatorProps {
  length: number;
  active: number;
  duration: number;
}

export const Indicators: FC<IndicatorProps> = props => {
  const { active, duration, length } = props;
  const arr = new Array(length).fill(' ');
  return (
    <div className="carousel-indicator">
      {arr.map((_: any, index: number) => {
        const isActive = active === index;
        return <ProgressBar duration={duration} active={isActive} />;
      })}
    </div>
  );
};
