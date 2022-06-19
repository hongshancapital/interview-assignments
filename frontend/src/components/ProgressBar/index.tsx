import React, { useEffect, useState } from "react";
import "./index.scss";

type Props = {
  width?: string;
  height?: string;
  borderRadius?: string;
  barColor?: string;
  progressColor?: string;
  duration?: number;
  run?: boolean;
  onProgressEnd?: () => void;
};

function ProgressBar(props: Props) {
  const {
    width = "100%",
    height = "2px",
    borderRadius = "1px",
    barColor = "rgb(170, 170, 170)",
    progressColor = "rgb(238, 238, 238)",
    duration = 3,
    run = false,
    onProgressEnd = () => {},
  } = props;

  const [running, setRunning] = useState(false);

  const handleProgressEnd = () => {
    setRunning(false);
    onProgressEnd();
  };

  useEffect(() => {
    setRunning(run);
  }, [run]);

  return (
    <div
      data-testid="progress-bar"
      className="progress-bar"
      style={{
        width,
        height,
        borderRadius,
      }}
    >
      <div
        data-testid="progress-bar-bar"
        className="progress-bar-bar"
        style={{ backgroundColor: barColor, borderRadius }}
      ></div>
      <div
        data-testid={"progress-bar-progress"}
        className={`progress-bar-progress ${
          running ? "progress-bar-progress-run" : ""
        }`}
        style={{
          transition: `width ${running ? duration : 0}s linear`,
          backgroundColor: progressColor,
          borderRadius,
        }}
        onTransitionEnd={handleProgressEnd}
      ></div>
    </div>
  );
}

export default ProgressBar;
