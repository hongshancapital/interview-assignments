import React, { useEffect, useRef, useState } from "react";
import "./progress.css";

export interface ProgressProps {
  timeout?: number;
  onTimeout: (i: number) => void;
  index: number; // 索引
  activeIndex: number; // 被激活的组件索引
}

const Progress: React.FC<ProgressProps> = (props: ProgressProps) => {
  const { index, activeIndex, timeout = 2000, onTimeout } = props;
  const [width, setWidth] = useState("0");
  const [margin, setMargin] = useState("0");
  const ref = useRef(0);

  useEffect(() => {
    function onProcess() {
      if (ref.current < 100) {
        // 每一帧的进度控制
        ref.current += (100.0 / timeout) * 16;
        setWidth(`${ref.current}%`);
        requestAnimationFrame(onProcess);
      } else {
        setWidth("100%");
        // 通知容器
        onTimeout(index);
      }
    }

    function reset() {
      if (ref.current > 0) {
        // 每一帧的进度控制
        ref.current -= 0.5 * 16;
        setWidth(`${ref.current}%`);
        setMargin(`${100 - ref.current}%`);
        requestAnimationFrame(reset);
      } else {
        setWidth("0");
        setMargin("0");
        ref.current = 0;
      }
    }

    if (activeIndex === index) {
      // TODO: 考虑polyfill
      requestAnimationFrame(onProcess);
    } else {
      requestAnimationFrame(reset);
    }
  }, [activeIndex, index, timeout, onTimeout]);
  return (
    <>
      <div className="progress-container">
        <div
          className="progress-percent"
          style={{
            width,
            marginLeft: margin,
          }}
        ></div>
      </div>
    </>
  );
};

export default Progress;
