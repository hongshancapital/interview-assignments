import { useRef, useState, useMemo, useEffect } from 'react';
import { SwipeItemRef } from '../CarouselItem';
import useRefs from './useRefs';

type Props = {
  deps: React.DependencyList;
  count: number;
  duration: number;
  loop: boolean;
};

export type SlideToParams = {
  step?: number;
  swiping?: boolean;
  offset?: number;
  itemSize?: number;
};

const useSwipe = ({ deps = [], count, duration, loop }: Props) => {
  const [size, setSize] = useState(0);
  const root = useRef(null);
  const [current, setCurrent] = useState(0);
  const realCurrent = useMemo(() => (current + count) % count || 0, [
    current,
    count
  ]);

  const swipeRef = useRef<HTMLDivElement>(null);
  const [refs, setRefs] = useRefs<SwipeItemRef>();
  const minCurrent = useMemo(() => (loop ? -1 : 0), [loop]);
  const maxCurrent = useMemo(() => (loop ? count : count - 1), [loop, count]);
  const loopDirection = useRef(1);

  const changeSize = () => {
    const rect = (root.current as any).getBoundingClientRect();
    if (rect) setSize(rect.width);
  };

  useEffect(() => {
    changeSize();
  }, deps);

  useEffect(() => {
    if (realCurrent === 0) {
      loopDirection.current = 1;
    }
    if (realCurrent === count - 1) {
      loopDirection.current = -1;
    }
  }, [realCurrent]);

  const setStyle = (
    dom: HTMLDivElement | null,
    options: { swiping: boolean; offset: number }
  ) => {
    if (!dom) return;
    const { swiping, offset } = options;
    dom.style.transition = `all ${swiping ? 0 : duration}ms`;
    dom.style.transform = `translateX(${offset}px)`;
  };

  const resetCurrent = () => {
    setStyle(swipeRef.current, {
      swiping: true,
      offset: -realCurrent * size
    });
  };

  const resetChild = (step: number, offset: number) => {
    let direction = '';
    if (step < 0 || offset > 0) {
      direction = 'left';
    }
    if (step > 0 || offset < 0) {
      direction = 'right';
    }
    if ([-1, count - 1].includes(current)) {
      refs[0].setOffset(direction === 'right' ? count * size : 0);
      refs[refs.length - 1].setOffset(0);
    }
    if ([count, 0].includes(current)) {
      refs[0].setOffset(0);
      refs[refs.length - 1].setOffset(
        direction === 'right' ? 0 : -count * size
      );
    }
  };

  const slideTo = ({
    step = 0,
    swiping = false,
    offset = 0
  }: SlideToParams) => {
    if (count <= 1) return;
    loop && resetChild(step, offset);
    const fetureCurrent = Math.min(
      Math.max(realCurrent + step, minCurrent),
      maxCurrent
    );
    const fetureOffset = -fetureCurrent * size + offset;
    if (swiping) {
      setStyle(swipeRef.current, {
        swiping,
        offset: fetureOffset
      });
    } else {
      requestAnimationFrame(() => {
        setStyle(swipeRef.current, {
          swiping,
          offset: fetureOffset
        });
      });
    }
    setCurrent(fetureCurrent);
  };

  const next = () => {
    resetCurrent();
    slideTo({ step: 1 });
  };

  const prev = () => {
    resetCurrent();
    slideTo({ step: -1 });
  };

  const loopMove = () => {
    if (loop) {
      next();
      return;
    }
    if (loopDirection.current === 1) {
      next();
    } else {
      prev();
    }
  };

  return {
    size,
    changeSize,
    root,
    swipeRef,
    setRefs,
    current: realCurrent,
    slideTo,
    resetCurrent,
    next,
    prev,
    loopMove
  };
};

export default useSwipe;
