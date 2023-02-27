import './style.css';

import React, { useState } from 'react';
import { CarouselProps } from './interface';
import Item from './Item';
import Progress from './Progress';

function Carousel(props: CarouselProps) {
  const {
    items = [],
    interval = 3000,
    prefixCls = 'carousel',
    height = '100%',
    width = '100%',
  } = props;
  const [current, setCurrent] = useState(0);

  return (
    <div
      className={`${prefixCls}-wrap`}
      style={{
        height,
        width,
      }}
    >
      <ul
        className={prefixCls}
        style={{
          width: `${items.length * 100}%`,
          left: `-${current * 100}%`,
        }}
      >
        {items.map((item) => (
          <Item
            key={item.key || item.title}
            {...item}
          />
        ))}
      </ul>
      <Progress
        interval={interval}
        count={items.length}
        value={current}
        onChange={setCurrent}
      />
    </div>
  );
}

export default Carousel;
