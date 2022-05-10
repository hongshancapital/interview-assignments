import React from "react";
import { CarouselIProps } from "../../index";

import "./style.scss";

interface Props {
  // 轮播图数据源
  items: CarouselIProps[];
  interval: number;
  activeIndex: number;
  callback?: (activeIndex: number) => void;
}

export default function Progress(props: Props): JSX.Element {
  const { items, interval = 0, activeIndex, callback } = props;

  const onClickItem = (activeIndex: number) => callback?.(activeIndex);

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
                className={
                  isActive ? "progress-item-active" : "progress-item-disable"
                }
                style={{ transition: isActive ? `${interval / 1000}s` : "" }}
              ></div>
            </div>
          </div>
        );
      })}
    </div>
  );
}
