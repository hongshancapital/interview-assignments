import React, { useState, useEffect, useRef } from "react";

interface ProgressProps {
  delay: number;
}

const Progress: React.FC<ProgressProps> = ({ delay }): React.ReactElement => {
  const [rate, setRate] = useState<number>(0);
  const progressTimer = useRef<NodeJS.Timer>();

  useEffect(() => {
    showProgress();
    return () => {
      progressTimer.current && clearInterval(progressTimer.current);
    };
  }, []);

  useEffect(() => {
    if (rate >= delay) {
      progressTimer.current && clearInterval(progressTimer.current);
    }
  }, [rate]);

  const showProgress = () => {
    progressTimer.current = setInterval(() => {
      setRate(rate => {
        return rate + 300;
      });
    }, 300);
  };

  return <div className="progress" style={{ width: `${(rate / delay) * 100}%` }}></div>;
};

export default Progress;
