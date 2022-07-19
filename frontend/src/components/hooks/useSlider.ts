import { useRef, useState } from 'react';
import { SliderItemRef } from '../carousel/SliderItem';
import useRefs from './useRefs';
type SliderOptions = {
  count: number;
  size: number;
  duration?: number;
  autoplay?: boolean;
};

/**
 * 封装slider行为逻辑
 * @param options
 */
const useSlider = (options: SliderOptions) => {
  const { autoplay, count, size, duration } = options;
  // 记录当前slider下标
  const [curIndex, setCurIndex] = useState(0);
  const [itemRefs, setRefs] = useRefs<SliderItemRef>();
  const sliderRef = useRef<HTMLDivElement>(null);


  const resetFirstChildOffset = (index: number) => {
    // 当前展示位最后item 移动firstChild 达到无缝展示
    if (index === count - 1) {
      itemRefs[0].setOffset(count * size);
    }
    // 重置偏移量
    if (index === 0) {
      itemRefs[0].setOffset(0);
    }
  };

  const setStyle = (
    dom: HTMLDivElement | null,
    options: { customizeDuration?: number; offset: number }
  ) => {
    if (!dom) return;
    const { customizeDuration = 0, offset } = options;
    dom.style.transition = `all ${customizeDuration}ms`;
    dom.style.transform = `translate${'X'}(${offset}px)`;
  };

  const slideByStep = (step: number) => {
    if (count < 2) {
      return;
    }
    const realCurrent = (curIndex + count) % count || 0;
    resetFirstChildOffset(realCurrent);
    const nextMoveIndex = Math.min(Math.max(realCurrent + step, -1), count);

    const nextMoveOffset = -nextMoveIndex * size;

    requestAnimationFrame(() => {
      requestAnimationFrame(() => {
        setStyle(sliderRef.current, {
          customizeDuration: duration,
          offset: nextMoveOffset,
        });
      });
    });
    setCurIndex(nextMoveIndex);
  };

  /**
   * 更具当前展示块设置contain 偏移量
   */
  const resetContainOffset = (curIndex: number) => {
    const realCurrent = (curIndex + count) % count || 0;
    setStyle(sliderRef.current, {
      offset: -realCurrent * size,
    });
  };

  const slideNext = () => {
    resetContainOffset(curIndex);
    slideByStep(1);
  };

  const slideToTargeIndex = (index: number) => {
    if (count < 2) {
      return;
    }
    const realCurrent = (index + count) % count || 0;
    resetFirstChildOffset(realCurrent);

    const nextMoveOffset = -index * size;

    requestAnimationFrame(() => {
      requestAnimationFrame(() => {
        setStyle(sliderRef.current, {
          customizeDuration: duration,
          offset: nextMoveOffset,
        });
      });
    });
    setCurIndex(realCurrent);
  }

  // 启动slider滑动循环
  const moveSlider = () => {
    if (autoplay) {
      slideNext();
    }
  };

  return {
    curIndex,
    sliderRef,
    moveSlider,
    setRefs,
    slideToTargeIndex,
  };
};

export default useSlider;
