import { useEffect } from 'react';
import { CarouselRef } from '..';

type UseAutoPlayHook = (props: {
  ref: React.ForwardedRef<CarouselRef>;
  auto: Boolean;
  active: number;
  duration: number;
  moveToNext: () => void;
}) => void;

/** 维护自动播放的逻辑 */
export const useAutoPlay: UseAutoPlayHook = ({
  auto,
  duration,
  moveToNext,
}) => {
  useEffect(() => {
    let timer: NodeJS.Timer;
    if (auto) {
      timer = setInterval(() => {
        moveToNext();
      }, Math.max(1000, duration));
    }
    return () => {
      timer && clearInterval(timer);
    };
  }, []);
};
