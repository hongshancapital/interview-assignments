import { DependencyList, EffectCallback, useEffect, useRef } from 'react';

export const useUpdateEffect = (effect: EffectCallback, deps: DependencyList) => {
  const firstRunRef = useRef(true);
  useEffect(() => {
    if (firstRunRef.current) {
      firstRunRef.current = false;
      return;
    }
    effect();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);
}