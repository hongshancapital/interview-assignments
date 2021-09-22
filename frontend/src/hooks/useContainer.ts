import { RefObject, useCallback } from 'react';
import { useRefGetter } from './useRefGetter';

export type UseContainerResult = () => HTMLElement | null;

export function useContainer(
  elem: RefObject<HTMLElement> | HTMLElement | null,
): UseContainerResult {
  const elemGetter = useRefGetter(elem);

  return useCallback(() => {
    const element = elemGetter();

    if (typeof window === 'undefined') {
      return null;
    }

    if (element == null || element instanceof HTMLElement) {
      return element;
    }

    return element.current;
  }, []);
}
