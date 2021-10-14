import React, { useEffect, useState } from 'react';
import { prefix, debug } from '../util';

/* -------------------------------- Animation ------------------------------- */

const slide = (offsetX = 0, offsetY = 0, duration = 0) => ({
  transform: `translate3d(${offsetX}00%, ${offsetY}00%, 0)`,
  transitionDuration: duration * 1000 + 'ms',
});

const slideX = (offsetX = 0, duration = 0) => slide(offsetX, 0, duration);
// const slideY = (offsetY: number = 0, duration: number = 0) => slide(0, offsetY, duration);

export interface CarouselTransitionProps {
  animation?: 'slideLeft' | 'slideRight'; // TODO | 'slideUp' | 'slideDown';
  step: number;
}

export const CarouselTransition: React.FC<CarouselTransitionProps> = function ({ step = 0, animation = 'slideLeft', children }) {
  const [animStyle, setAnimStyle] = useState(slideX(step));

  debug('[CarouselTransition:render]');

  useEffect(() => {
    const slideDir = {
      slideLeft: -1,
      slideRight: 1,
      slideUp: -1,
      slideDown: 1,
    }[animation];

    setAnimStyle(slideX(slideDir * step, 1));
  }, [animation, step]);

  // 1. css direction: rtl | ltr
  // 2. html dir="rtl" or dir="ltr"
  // rtl 控制 CarouselItem 从右向左排列 例如:  3 2 |1|
  // ltr 控制 CarouselItem 从左向右排列 例如:      |1| 2 3
  const dir = animation === 'slideRight' ? 'rtl' : 'ltr';
  return (
    <div className={prefix('carousel-transition')} style={animStyle} dir={dir}>
      {children}
    </div>
  );
};
