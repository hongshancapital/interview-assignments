import { useEffect, useRef, useState } from "react";

/**
 * 自定义的定时器hooks
 * @param count 总页数
 * @param duration 切换周期
 * @returns 当前页码index、设置当前页码（可用于重新启动定时器）、定时器对象（可用于关闭定时器）
 */
const usePlay = (count: number, duration: number) => {
  const [step, setStep] = useState<{ index: number }>({ index: 0 });
  const timer = useRef<NodeJS.Timer>();

  useEffect(() => {
    timer.current = setInterval(() => {
      if (count > 0) {
        setStep({index: (step.index + 1) % count});
      }
    }, duration);
    return () => {
      if (timer.current) {
        clearInterval(timer.current)
      }
    };
  }, [step, duration, count]);

  const index = step.index;
  const setCurrentStep = (current: { index: number }) => {
    if (count > 0) {
      setStep({index: (current.index + 1) % count});
    } else {
      setStep({index: 0});
    }
  }

  return { index, setCurrentStep, timer };

};

export default usePlay;