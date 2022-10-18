import React, { useEffect, useMemo, MouseEventHandler } from 'react';
import './index.css';

const BAR_WIDTH = 70;
const SPACE_WIDTH = 10;

const ProgressBar: React.FC<{
  count: number;
  activeIndex: number;
  duration: number;
  onProgressItemClick: MouseEventHandler<HTMLDivElement>;
}> = ({ count, activeIndex, duration, onProgressItemClick }) => {
  useEffect(() => {}, [count, activeIndex]);
  const progressList = useMemo(() => {
    let arr = [];

    for (let index = 0; index < count; index++) {
      arr[index] = index;
    }

    return arr;
  }, [count]);

  return (
    <div
      className="progress-bar"
      style={{ width: `${count * (BAR_WIDTH + SPACE_WIDTH)}px` }}
    >
      {progressList.map(index => (
        <div
          className="progress-item"
          key={index}
          onClick={onProgressItemClick}
          data-index={index}
        >
          {activeIndex === index && (
            <div
              style={{
                animationDuration: `${duration}ms`,
              }}
              className="progress-content"
            />
          )}
        </div>
      ))}
    </div>
  );
};

export default ProgressBar;
