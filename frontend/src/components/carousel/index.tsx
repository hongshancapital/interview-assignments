import React, { useMemo, useCallback } from "react";
import Item from "./components/carousel-item/index";
import Progress from "./components/progress/index";

import { useActiveIndex } from "./hooks/useActiveIndex";

import "./style.scss";

export interface CarouselIProps {
  id: string;
  src: string;
  title: string[];
  desc?: string[];
  alt?: string;
  color?: string;
}
interface Props {
  // 轮播图数据源
  items: CarouselIProps[];
  // 时间间隔
  interval?: number;
}

const Carousel = (props: Props): JSX.Element => {
  const { items, interval = 3000 } = props;

  const [activeIndex, setActiveIndex] = useActiveIndex({
    interval,
    count: items.length,
  });

  const onClickProgress = useCallback(
    (index: number) => {
      setActiveIndex(index);
    },
    [setActiveIndex]
  );

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
        interval={interval}
        activeIndex={activeIndex}
        callback={onClickProgress}
      />
    </div>
  );
};

export default Carousel;
