import React, { useEffect, useState, useRef, useCallback } from 'react';

import './progressBar.scss';

interface ProgressBarProps {
  idx: number;
  selected: boolean;
  onClick: () => void;
}

export interface ProgressControlProps {
  autoplay: boolean;
  isPlaying: boolean;
  duration: number;
}

const ProgressBar: React.FC<ProgressBarProps & ProgressControlProps> = ({
  selected,
  autoplay,
  isPlaying,
  duration,
  onClick
}) => {
  const [width, setWidth] = useState(0);
  const intervalRef = useRef<number>();
  const widthRef = useRef(width);

  const setWidthAndRef = (width: number) => {
    setWidth(width);
    widthRef.current = width;
  };

  const play = useCallback(() => {
    stop();
    intervalRef.current = window.setInterval(() => {
      const width = widthRef.current;

      if (width >= 100) {
        stop();
        return;
      }
      setWidthAndRef(width + 1);
    }, duration / 100);
  }, [duration]);

  const stop = () => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
    }
  }

  useEffect(() => {
    setWidth(autoplay ? 0 : 100);
  }, [autoplay]);

  useEffect(() => {
    if (!autoplay) {
      return;
    }

    if (!selected) {
      stop();
      setWidthAndRef(0);
      return;
    }

    if (isPlaying) {
      play();
    } else {
      stop();
    }
  }, [selected, autoplay, isPlaying, play]);

  return (
    <div className="carousel-progress" onClick={onClick}>
      <div className="progress-bar" style={{ width: `${width}%` }} />
    </div>
  );
};

export default ProgressBar;
