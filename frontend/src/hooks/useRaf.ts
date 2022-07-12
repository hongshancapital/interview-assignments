import { useState, useEffect } from "react";

export default function useRaf(playSpeed: number, onPlayEnded: () => void) {
  const [progress, updateProgress] = useState(0);

  useEffect(() => {
    let raf: any;
    let start = 0;
    const step = (timeStamp: number) => {
      if (start) {
        const duration = timeStamp - start;
        if (duration >= playSpeed) {
          updateProgress(100);
          onPlayEnded();
          return;
        }
        updateProgress(Math.floor((duration / playSpeed) * 100));
      } else {
        start = timeStamp;
      }
      raf = window.requestAnimationFrame(step);
    };

    raf = window.requestAnimationFrame(step);

    return () => {
      window.cancelAnimationFrame(raf);
    };
  }, []);

  return progress;
}
