import { RefObject, useCallback, useLayoutEffect, useRef, useState } from 'react'
import { SlideDirection } from "../../types/carousel";
import useEventListener from '../useEventListener/useEventListener';

type CarouselTypes = {
  count: number,
  duration: number
}

type CurIndex = number;

type Actions = {
  slideToDirection: (direction?: SlideDirection) => void,
  slideToIndex: (nextIndex: CurIndex) => void
}
type ReturnValueActions = [ CurIndex, Actions ];

/**
 * useSlider 轮播图滑动钩子
 * @param carouselRef 轮播图容器
 * @param types.count 轮播图数量
 * @param types.duration 切换的过渡时间
 * @returns [ currentIndex, { slide, slideTo } ]
 */
function useSlider(carouselRef: RefObject<HTMLElement>, types: CarouselTypes): ReturnValueActions {
  const { count, duration } = types; 
  const LAST_INDEX = count;
  const FIRST_INDEX = 1;
  const currentIndexRef = useRef(count > 1 ? FIRST_INDEX : 0);
  const [ currentStateIndex, setCurrentStateIndex ] = useState(FIRST_INDEX);
  const isSlideing = useRef(false); // 动画过渡中...

  // 初始化重置轮播图位置
  useLayoutEffect(() => {
    if (!carouselRef.current) return;
    carouselRef.current.style.transform = `translateX(-${currentIndexRef.current}00%)`;
  });

  // 按定位滑动轮播图
  const slideToIndex = useCallback((index) => {
      if(!carouselRef.current || (index < 0 && index > count)) return;
      currentIndexRef.current = index;
      carouselRef.current.style.transitionDuration = `${duration}ms`;
      carouselRef.current.style.transform = `translateX(-${currentIndexRef.current}00%)`;
      setCurrentStateIndex(currentIndexRef.current);
  }, []);

  // 按方位滑动轮播图
  const slideToDirection = useCallback((direction: SlideDirection = SlideDirection.Right) => {
    if (!carouselRef.current || count < 2 || isSlideing.current) return;

    isSlideing.current = true;

    if (direction === SlideDirection.Right) {
      currentIndexRef.current++;
    } else {
      currentIndexRef.current--;
    }

    // 正常的动画过渡
    carouselRef.current.style.transitionDuration = `${duration}ms`;
    carouselRef.current.style.transform = `translateX(-${currentIndexRef.current}00%)`;

    // 触发页面更新
    let currentStateIndex = currentIndexRef.current;
  
    currentStateIndex = isCrossIndex(currentStateIndex, LAST_INDEX, FIRST_INDEX);

    setCurrentStateIndex(currentStateIndex);
  }, []);

  // 监听css动画结束事件，重置轮播位置，完成无缝切换
  useEventListener('transitionend', () => {
    isSlideing.current = false;

    const newIndex = isCrossIndex(currentIndexRef.current, LAST_INDEX, FIRST_INDEX);
    if (newIndex === currentIndexRef.current || !carouselRef.current) {
      return;
    }
  
    currentIndexRef.current = newIndex
    carouselRef.current.style.transitionDuration = '0ms';
    carouselRef.current.style.transform = `translateX(-${currentIndexRef.current}00%)`;

  }, carouselRef);

  return [currentStateIndex, { slideToDirection, slideToIndex }];
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