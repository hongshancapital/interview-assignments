import React, { FC, ReactNode, useState } from 'react';

import CarouselItem from './CarouselItem';
import { Indicators } from './Indicators';
import { useInterval, useMount } from './utils';

export interface CarouselProps {
  children?: ReactNode;

  /** hover时是否停止播放 */
  stopOnHover?: boolean;
  /** 动画过渡时间（s） */
  duration?: number;
  /** 图片停留时间（s） */
  interval?: number;
  /** 动画模式  */
  animation?: 'fade' | 'slide';
  /** 动画播放后当前页和前一页 */
  onChange?: (now?: number, previous?: number) => any;
}

export const Carousel: FC<CarouselProps> = props => {
  const { stopOnHover = true, interval = 1, duration = 3, children, animation = 'slide' } = props;

  const [paused, setPaused] = useState<boolean>(false);

  const [state, setState] = useState({
    active: 0,
    prevActive: 0,
  });

  useMount(() => {
    setState({
      active: 0,
      prevActive: 0,
    });
  });

  useInterval(() => {
    if (!paused) {
      next();
    }
  }, (interval + duration) * 1000);

  const next = () => {
    const last = Array.isArray(children) ? children.length - 1 : 0;
    const nextActive = state.active + 1 > last ? 0 : state.active + 1;
    setState({
      active: nextActive,
      prevActive: state.active,
    });
  };

  return (
    <div
      className="carousel-root"
      onFocus={() => {
        stopOnHover && setPaused(true);
      }}
      onBlur={() => {
        stopOnHover && setPaused(false);
      }}
      onMouseOver={() => {
        stopOnHover && setPaused(true);
      }}
      onMouseOut={() => {
        stopOnHover && setPaused(false);
      }}
    >
      <div className="carousel-item-wrapper">
        {Array.isArray(children) ? (
          children.map((child, index) => {
            return (
              <CarouselItem
                key={`carousel-item${index}`}
                state={state}
                index={index}
                maxIndex={children.length - 1}
                child={child}
                animation={animation}
                duration={duration}
              />
            );
          })
        ) : (
          <div>暂无数据</div>
        )}
      </div>

      <Indicators
        length={Array.isArray(children) ? children.length : 0}
        active={state.active}
        duration={duration}
      />
    </div>
  );
};

export default Carousel;
