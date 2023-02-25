import { useState } from "react";
import useInterval from "use-interval";

const frame = 10;

/**
 * @param sliderCnt total number of slider
 * @param interval 3000ms default
 */
export default function useSlider(sliderCnt: number, interval = 3000): [number, number] {
  // progress of current slider: 0 <= & <= 100
  const [progress, updateProgress] = useState(0);

  // current slider: 0 <= & < sliderCnt
  const [current, switchTo] = useState(0);

  // or animationend
  useInterval(() => {
    const newProgress = progress + 100 / frame;
    updateProgress(newProgress > 100 ? 0 : newProgress);
    if (newProgress >= 100) {
      switchTo((current) => {
        const next = current + 1;
        return next > sliderCnt ? 0 : next;
      });
    }
  }, interval / frame);

  return [current, progress];
}
