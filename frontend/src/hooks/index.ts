import { RefObject, useCallback, useMemo, useRef } from 'react';

export type UseContainerResult = () => HTMLElement | null;



export function useSingleton<T>(creator: () => T): T {
    const ref = useRef<T | null>(null);
  
    return useMemo(() => {
      if (ref.current) {
        return ref.current;
      }
  
      return (ref.current = creator());
    }, []);
}

export function useRefGetter<T>(value: T): () => T {
    const ref = useRef(value);
  
    ref.current = value;
  
    return useCallback(() => ref.current, []);
}
  

export function useRefCallback<T extends(...args: any[]) => any>(callback: T) {
  const callbackRef = useRef(callback);
  callbackRef.current = callback;
  return useCallback((...args: any[]) => callbackRef.current(...args), []) as T;
}