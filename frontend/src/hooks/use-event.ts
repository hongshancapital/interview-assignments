import { useLayoutEffect, useRef } from 'react';

type AnyFunction = (...args: any[]) => any;

const noop = () => {};

export function useEvent<T extends AnyFunction>(fn: T): T {
  const latestRef = useRef<T>(noop as any);

  useLayoutEffect(() => {
    latestRef.current = fn;
  }, [fn]);

  const stableRef = useRef<T>(null as any);
  if (!stableRef.current) {
    stableRef.current = function (this: any) {
      return latestRef.current.apply(this, arguments as any);
    } as T;
  }

  return stableRef.current;
}

export default useEvent;
