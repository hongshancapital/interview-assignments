import { AnimatePresence, motion } from 'framer-motion';
import React, { FC, ReactNode } from 'react';
export interface CarouselItemProps {
  animation: 'fade' | 'slide';
  state: {
    active: number;
    prevActive: number;
  };
  index: number;
  maxIndex: number;
  duration: number;
  child: ReactNode;
}
export const CarouselItem: FC<CarouselItemProps> = props => {
  const { animation = 'slide', state, index, maxIndex, child, duration } = props;

  const slide = animation === 'slide';
  const fade = animation === 'fade';

  const variants = {
    leftwardExit: {
      x: slide ? '-100%' : undefined,
      opacity: fade ? 0 : undefined,
      zIndex: 0,
    },
    leftOut: {
      x: slide ? '-100%' : undefined,
      opacity: fade ? 0 : undefined,
      display: 'none',
      zIndex: 0,
    },
    rightwardExit: {
      x: slide ? '100%' : undefined,
      opacity: fade ? 0 : undefined,
      zIndex: 0,
    },
    rightOut: {
      x: slide ? '100%' : undefined,
      opacity: fade ? 0 : undefined,
      display: 'none',
      zIndex: 0,
    },
    center: {
      x: 0,
      opacity: 1,
      zIndex: 1,
    },
  };

  const { active, prevActive } = state;
  let animate = 'center';
  if (index === active) {
    animate = 'center';
  } else if (index === prevActive) {
    animate = 'leftwardExit';
    if (active === maxIndex && index === 0) animate = 'rightwardExit';
    if (active === 0 && index === maxIndex) animate = 'leftwardExit';
  } else {
    animate = index < active ? 'leftOut' : 'rightOut';
    if (active === maxIndex && index === 0) animate = 'rightOut';
    if (active === 0 && index === maxIndex) animate = 'leftOut';
  }
  console.log('animate', animate, state, index);

  return (
    <div className="carousel-item">
      <AnimatePresence>
        <motion.div style={{ height: '100%' }}>
          <motion.div
            variants={variants}
            animate={animate}
            transition={{
              x: { type: 'tween', duration: duration, delay: 0 },
              opacity: { duration: duration },
            }}
            style={{ position: 'relative', height: '100%' }}
          >
            {child}
          </motion.div>
        </motion.div>
      </AnimatePresence>
    </div>
  );
};

export default CarouselItem;
