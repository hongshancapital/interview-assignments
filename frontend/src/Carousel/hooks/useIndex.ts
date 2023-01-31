import { useEffect, useState } from 'react';

type UseIndexProps = { duration: number; childrenLength: number };

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

  return { currIndex };
};
