import { useState, useEffect, Dispatch, SetStateAction } from "react";

interface Props {
  defaultIndex?: number;
  interval?: number;
  count: number;
}

type UseActiveType = [
  activeIndex: number,
  setActiveIndex: Dispatch<SetStateAction<number>>
];

export function useActiveIndex(props: Props): UseActiveType {
  const { defaultIndex = 0, interval = 3000, count } = props;

  const [index, setIndex] = useState(defaultIndex);

  useEffect(() => {
    const timer = setInterval(() => {
      const i = (index + 1) % count;
      setIndex(i);
    }, interval);

    return () => {
      timer && clearInterval(timer);
    };
  }, [index, count, interval]);

  return [index, setIndex];
}
