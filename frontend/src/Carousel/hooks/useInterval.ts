import { useEffect, useRef } from "react";

export function useInterval(callback: () => void, delay: number | null) {
  const callbackRef = useRef<Function>();

  useEffect(() => {
    callbackRef.current = callback;
  });

  useEffect(() => {
    if (delay !== null) {
      const id = setInterval(() => callbackRef.current?.(), delay) as any;
      return () => clearInterval(id);
    }
  }, [delay]);
}
