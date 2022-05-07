import React from "react";
import { CarouselIProps } from "../carousel-item/index";

import "./style.scss";

interface Props {
  // 轮播图数据源
  items: CarouselIProps[];
  time: number;
  activeIndex: number;
  callback?: (activeIndex: number) => void;
}

export default function Progress(props: Props): JSX.Element {
  const { items, time, activeIndex, callback } = props;

  const onClickItem = (activeIndex: number) =>
    callback && callback(activeIndex);

  return (
    <div className="progress-container">
      {items.map((e, index) => {
        const isActive = index === activeIndex;
        return (
          <div
            key={e.id}
            className="progress-view"
            onClick={() => onClickItem(index)}
          >
            <div className="progress-item">
              <div
                className={isActive ? "active" : "disable"}
                style={{ transition: isActive ? `${time / 1000}s` : "" }}
              ></div>
            </div>
          </div>
        );
      })}
    </div>
  );
}
