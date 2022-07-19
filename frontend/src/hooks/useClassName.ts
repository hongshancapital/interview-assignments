import { useMemo } from 'react';

export const useClassName = (...classNames: (string | null | undefined)[]): string | undefined => {
  return useMemo(() => {
    const cls = classNames.filter(it => it != null && it).join(' ');
    if (cls.trim() === '') {
      return undefined;
    }
    return cls;
  }, [classNames]);
};
