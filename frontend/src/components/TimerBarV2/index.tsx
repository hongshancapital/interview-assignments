import React, { useState } from "react";
import "./index.scss";

interface ITimerBar {
  // 是否循环播放，如果不传，默认为true
  isCycle?: boolean;
  // 时间间隔，不传默认为3s
  time?: number;
  // 时间条数量，默认为1
  num?: number;
  // 到时 回调
  onTime?: (index: number) => any;
  className?: string;
}

const TimerBar = (props: ITimerBar) => {
  const { isCycle = true, time = 3, num = 1, onTime, className = "" } = props;
  const [curIndex, setCurIndex] = useState<number>(0);
  const arr = [...Array(num)].map((_, index) => index);

  const next = () => {
    let nextIndex = curIndex + 1;
    if (isCycle && curIndex >= num - 1) {
      // 循环播放，index重新回到0
      nextIndex = 0;
    }
    onTime?.(nextIndex);
    setCurIndex(nextIndex);
  };

  return (
    <div className={`${className} tbContainer`}>
      {arr.map((v, index) => (
        <div className="timeBar" key={index}>
          <div
            onAnimationEnd={next}
            className={`goingLine${index === curIndex ? " run" : ""}`}
            style={{ animationDuration: `${time}s` }}
          />
        </div>
      ))}
    </div>
  );
};

export { TimerBar };
