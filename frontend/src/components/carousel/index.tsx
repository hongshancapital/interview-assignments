import React, { useMemo, useCallback } from "react";
import Item, { CarouselIProps } from "./components/carousel-item/index";
import Progress from "./components/progress/index";

import { useActiveIndex } from "./hooks/useActiveIndex";

import "./style.scss";

interface Props {
  // 轮播图数据源
  items: CarouselIProps[];
  // 时间间隔
  interval?: number;
}

const Carousel = (props: Props): JSX.Element => {
  const { items, interval = 3000 } = props;

  const count = useMemo(() => items.length, [items]);

  const [activeIndex, setActiveIndex] = useActiveIndex({
    interval,
    count,
  });

  const onClickProgress = useCallback((index: number) => {
    setActiveIndex(index);
  }, []);

  return (
    <div className="carousel">
      <div
        className="carousel-items"
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {items.map((item) => {
          return <Item {...item} key={item.id}></Item>;
        })}
      </div>
      <Progress
        items={items}
        time={interval}
        activeIndex={activeIndex}
        callback={onClickProgress}
      />
    </div>
  );
};

export default Carousel;
