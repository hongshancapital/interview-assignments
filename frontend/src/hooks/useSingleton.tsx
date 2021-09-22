import { useMemo, useRef } from 'react';

export function useSingleton<T>(creator: () => T): T {
  const ref = useRef<T | null>(null);

  return useMemo(() => {
    if (ref.current) {
      return ref.current;
    }

    return (ref.current = creator());
  }, []);
}
