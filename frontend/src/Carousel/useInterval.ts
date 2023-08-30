import { useRef, useEffect } from "react";

function useInterval(callback: Function, delay?: number | null) {
  const savedCallback = useRef<Function>(() => {});

  // Remember the latest function.
  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  // Set up the interval.
  useEffect(() => {
    if (delay !== null) {
      let id = setInterval(() => savedCallback.current(), delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}
export default useInterval;
