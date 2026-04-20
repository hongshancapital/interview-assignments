import { useEffect, useState } from 'react';

type UseIndexProps = {
  /** Mille seconds duration for each index */
  duration: number;
  /** Number of children in the carousel */
  childrenLength: number;
};

/**
 * Calculates current child index based on duration and children length
 * @returns current index
 */
export const useIndex = ({ duration, childrenLength }: UseIndexProps) => {
  const [currIndex, setCurrIndex] = useState(0);

  useEffect(() => {
    const id = setInterval(() => {
      setCurrIndex((currIndex) =>
        currIndex >= childrenLength - 1 ? 0 : currIndex + 1
      );
    }, duration);

    return () => {
      clearInterval(id);
    };
  }, [duration, childrenLength]);

  return currIndex;
};
