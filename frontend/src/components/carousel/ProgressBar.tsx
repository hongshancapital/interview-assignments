import { motion } from 'framer-motion';
import React from 'react';

export const ProgressBar = ({
  percents = 100,
  duration = 3,
  easing = 'linear',
  barWidth = 50,
  barHeight = 2,
  progressColor = 'rgb(248 248 248)',
  baseColor = 'rgb(153 153 153)',
  active = false,
}) => {
  const percentsOffset = (percents - 100) * (barWidth / 100);

  const transition = {
    duration: duration,
    delay: 0,
    ease: easing,
  };

  const variants = {
    enter: {
      x: -barWidth,
      transition: {
        duration: 0,
      },
    },
    animate: {
      x: [-barWidth, percentsOffset],
      transition,
    },
  };

  return (
    <div className="flex">
      <div
        className="bar"
        style={{
          width: barWidth,
          height: barHeight,
          backgroundColor: baseColor,
        }}
      >
        <motion.div
          className="bar-filling"
          variants={variants}
          initial="enter"
          animate={active ? 'animate' : 'enter'}
          exit="enter"
          style={{
            backgroundColor: progressColor,
          }}
        />
      </div>
    </div>
  );
};
