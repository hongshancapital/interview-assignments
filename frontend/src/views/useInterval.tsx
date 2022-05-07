import { useEffect, useRef } from "react";

type cbType = (...args: any[]) => void;

function useInterval(cb: cbType, delay: number | null) {
  const cbRef = useRef<cbType>();

  useEffect(() => {
    cbRef.current = cb;
  }, [cb]);

  useEffect(() => {
    const tick = () => {
      if (cbRef.current) {
        cbRef.current();
      }
    };

    if (delay !== null) {
      const timer = setInterval(tick, delay);
      return () => {
        clearInterval(timer);
      };
    }
  }, [delay]);
}

export default useInterval;
