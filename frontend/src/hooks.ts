import { useRef, useEffect } from "react";

export function useInterval() {
  const ref = useRef(0);
  const set: typeof setInterval = ((fn: Function, ms: number) => {
    clear();
    ref.current = setInterval(fn, ms);
    return ref.current;
  }) as any;
  const clear = () => clearInterval(ref.current);
  useEffect(() => () => clear(), []);
  return { set, clear };
}
