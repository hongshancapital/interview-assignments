import { useCallback, useRef, useState } from 'react'
import { SlideDirection } from "../../types/carousel";

type CurIndex = number;

type Actions = {
  slideToDirection: (direction?: SlideDirection) => void,
  slideToIndex: (nextIndex: CurIndex) => void
}
type ReturnValueActions = [ CurIndex, Actions ];

/**
 * useSlider 轮播图滑动钩子
 * @param count 轮播图数量
 * @returns [ currentIndex, { slideToIndex, slideToDirection } ]
 */
function useSlider(count: number): ReturnValueActions {
  const currentIndexRef = useRef(0);
  const [ currentStateIndex, setCurrentStateIndex ] = useState(0);

  // 按定位滑动轮播图
  const slideToIndex = useCallback((index: number) => {
      currentIndexRef.current = index;
      setCurrentStateIndex(currentIndexRef.current);
  }, []);

  // 按方位滑动轮播图
  const slideToDirection = useCallback((direction: SlideDirection = SlideDirection.Right) => {
    if (count < 1) return;
    if (direction === SlideDirection.Right) {
      currentIndexRef.current++;
    } else {
      currentIndexRef.current--;
    }

    currentIndexRef.current = isCrossIndex(currentIndexRef.current, count - 1, 0);
    setCurrentStateIndex(currentIndexRef.current);

  }, [count]);

  return [ currentStateIndex, { slideToDirection, slideToIndex } ];
}

export function isCrossIndex<T = number> (current: T, end: T, start: T) {
    // 尾屏 - 当滑动越过最后一帧，重置到第一帧
    if (current > end) {
      current = start;
    }

    // 首屏 - 当滑动越过第一帧，重置到最后一帧
    if (current < start) {
      current = end;
    }
    return current
}

export default useSlider;