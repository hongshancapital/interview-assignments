import React from 'react';
import cs from 'classnames';
import { CarouselProgressProps } from './interface';

function Progress(props: CarouselProgressProps) {
  const {
    prefixCls = 'carousel-progress',
    count = 3,
    value = 0,
    interval = 5000,
    onChange
  } = props;

  return (
    <ul
      className={prefixCls}
    >
      {new Array(count).fill(null).map((_, i) => (
        <li
          key={i}
          className={cs(`${prefixCls}-item`, {
            [`${prefixCls}-active`]: value === i,
          })}
        >
          <div
            className={`${prefixCls}-inner`}
            style={{
              animationDuration: `${interval}ms`,
            }}
            onAnimationEnd={() => {
              onChange?.((value + 1) % count);
            }}
          />
        </li>
      ))}
    </ul>
  );
}

export default Progress;
