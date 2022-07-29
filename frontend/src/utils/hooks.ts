import * as React from 'react';

export function useInterval(callback: () => void, intervalMs: number): void {
  const callbackRef = React.useRef(callback);

  React.useLayoutEffect(() => {
    callbackRef.current = callback;
  })

  React.useEffect(() => {
    const intervalHandle = setInterval(() => callbackRef.current(), intervalMs);
    return () => clearInterval(intervalHandle);
  }, [intervalMs]);
}