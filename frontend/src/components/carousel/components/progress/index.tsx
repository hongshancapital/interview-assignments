import React, { useCallback } from "react";
import "./style.scss";

interface Props {
  count: number;
  time: number;
  activeIndex: number;
  callback?: (activeIndex: number) => void;
}

export default function Progress(props: Props): JSX.Element {
  const { count, time, activeIndex, callback } = props;

  const onClickItem = (activeIndex: number) =>
    callback && callback(activeIndex);

  return (
    <div className="progress-container">
      {Array.from({ length: count }).map((_, index) => {
        const isActive = index === activeIndex;
        return (
          <div
            key={index + ""}
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
