import React from 'react';

import ProgressBar, { ProgressControlProps } from './ProgressBar';

import './indicator.scss';

interface IndicatorProps {
  goTo: (idx: number) => void;
  autoplay: boolean;
  isPlaying: boolean;
  duration: number;
  count: number;
  current: number;
}

const Indicator: React.FC<IndicatorProps & ProgressControlProps> = ({
  goTo,
  count,
  current,
  ...props
}) => {
  const renderProgressBars = new Array(count).fill('').map((_, idx) => {
    const selected = idx === (current % count);
    const onSelect = () => {
      if (!selected) {
        goTo(idx);
      }
    }

    return (
      <ProgressBar
        key={idx}
        idx={idx}
        selected={selected}
        onClick={onSelect}
        {...props}
      />
    );
  })

  return (
    <div className="carousel-indicator">
      <div className="indicator-wrapper">
        {renderProgressBars}
      </div>
    </div>
  );
};

export default Indicator;
