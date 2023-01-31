import React from 'react';
import styles from './index.module.scss';
import cx from 'classnames'

export interface CarouselDotProps {
  dotNumber?: number;
  currentFrameNumber: number;
  onDotClick?: (dotIndex: number) => void;
}

const CarouselDot: React.FC<CarouselDotProps> = (props) => {
  const { dotNumber = 0, onDotClick, currentFrameNumber } = props;

  return (
    <div className={styles.carouselDotContainer}>
      {Array.from({ length: dotNumber }, (__, index) => (
        <div key={index} className={cx(styles.carouselDotItem, index === currentFrameNumber ? styles.active : '')} onClick={() => onDotClick?.(index)}>
        </div>
      ))}
    </div>
  )
}

export default CarouselDot;
