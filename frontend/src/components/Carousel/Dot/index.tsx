import React, { useCallback, useRef, useState } from "react";
import "./index.css";

export type DotStatus = "loading" | "sleep";

interface DotProps {
  status: DotStatus;
  dotWidth: number;
  index: number;
  cycleTime: number;
  onTimeEnd: (index: number) => void;
}

const Dot = (props: DotProps) => {
  const { status, onTimeEnd, index, cycleTime, dotWidth } = props;
  const [width, setWidth] = useState(0);
  const insData = useRef<{
    fired: boolean;
  }>({
    fired: false,
  });

  const loading = status === "loading";

  const fireAnimation = useCallback(
    (lastWidth: number) => {
      const _fireAnimation = (lastWidth: number) => {
        window.requestAnimationFrame(() => {
          const distance = dotWidth / (cycleTime / 16.6) + lastWidth;
          setWidth(distance);
          if (distance > dotWidth) {
            setWidth(0);
            onTimeEnd?.(index);
            return;
          }
          _fireAnimation(distance);
        });
      };
      _fireAnimation(lastWidth);
    },
    [cycleTime, dotWidth, index, onTimeEnd]
  );

  React.useEffect(() => {
    if (loading && !insData.current.fired) {
      fireAnimation(width);
      insData.current.fired = true;
    }
    if (!loading) {
      /* 便于下次重新fired */
      insData.current.fired = false;
    }
  }, [dotWidth, fireAnimation, loading, width]);

  const dotStyle = {
    width: `${dotWidth}px`,
  };

  const dotActiveStyle = {
    width: `${width}px`,
  };

  return (
    <span className="dot" style={dotStyle}>
      <span style={dotActiveStyle}></span>
    </span>
  );
};

export default Dot;
