import React, { useEffect, useState } from "react";
import "./index.scss";

type Props = {
  width?: string;
  height?: string;
  barColor?: string;
  progressColor?: string;
  duration?: number;
  run?: boolean;
};

function ProgressBar(props: Props) {
  const {
    width = "100%",
    height = "30px",
    barColor = "#999",
    progressColor = "#222",
    duration = 3,
    run = false,
  } = props;

  const [running, setRunning] = useState(false);

  const handleProgressEnd = () => {
    setRunning(false);
  };

  useEffect(() => {
    setRunning(run);
  }, [run]);

  return (
    <div
      className="progress-bar"
      style={{
        width,
        height,
      }}
    >
      <div
        className="progress-bar-bar"
        style={{ backgroundColor: barColor }}
      ></div>
      <div
        className={`progress-bar-progress ${
          running ? "progress-bar-progress-run" : ""
        }`}
        style={{
          transition: `width ${running ? duration : 0}s linear`,
          backgroundColor: progressColor,
        }}
        onTransitionEnd={handleProgressEnd}
      ></div>
    </div>
  );
}

export default ProgressBar;
