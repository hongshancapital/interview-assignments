import { memo, FC } from 'react';
import Progress from '../../Progress';
import './index.css';

interface SlickDotsProps {
  /** number of panel indication points */
  length: number;
  /** currently selected index value */
  activeIndex: number;
  /** animated duration */
  duration?: number;
  /** click event */
  onClick?: (index: number) => void;
}

const SlickDots: FC<SlickDotsProps> = function ({
  length,
  activeIndex,
  duration = 1,
  onClick,
}) {
  const renderDot = () => {
    let list = [];
    for (let i = 0; i < length; i++) {
      const isActive = activeIndex === i;
      list.push(
        <Progress
          key={i}
          percent={isActive ? '100%' : '0%'}
          barStyle={isActive ? { transition: `${duration}s all` } : undefined}
          onClick={() => onClick?.(i)}
        />
      );
    }
    return list;
  };
  return <div className="carousel-dots">{renderDot()}</div>;
};

export default memo(SlickDots);
