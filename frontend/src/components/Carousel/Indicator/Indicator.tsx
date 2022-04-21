import React from 'react';

import { State } from '../helper';
import { IndicatorItem, ProgressBar } from './styles';

interface IndicatorProps {
  index: number;
  state: State;
  duration: number;
  onClick: (index: number) => void;
}

export const Indicator = (props: IndicatorProps): JSX.Element => {
  const { index, state, duration, onClick } = props;

  const isCurrentIndicator = state === 'current';

  return (
    <IndicatorItem data-testid="indicator-item" onClick={() => onClick(index)}>
      {isCurrentIndicator && (
        <ProgressBar duration={duration} data-testid="progress-bar" />
      )}
    </IndicatorItem>
  );
};
