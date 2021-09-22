import { RefObject, useEffect, useRef } from 'react';
import { useRefGetter }from './useRefGetter';

import {
  addMutationHandler,
  getDefaultElementSize,
  getElementSize,
  isElementSizeEqual,
  removeMutationHandler,
  IElementSize
} from '../util';
import { useContainer } from './useContainer';
import { ResizeHandler } from './useWindowSize';

export function useElementSize(
  element: HTMLElement | null | RefObject<HTMLElement>,
  enable: () => boolean,
  onResize: ResizeHandler,
): IElementSize {
  const sizeRef = useRef(getDefaultElementSize());
  const elementGetter = useContainer(element);
  const enableGetter = useRefGetter(enable);
  const initRef = useRef(false);
  const onResizeGetter = useRefGetter(onResize);

  useEffect(() => {
    const elem = elementGetter();

    if (elem == null) {
      return;
    }

    const update = (): void => {
      const currentSize = getElementSize(elem);
      const oldSize = sizeRef.current;

      if (!isElementSizeEqual(sizeRef.current, currentSize)) {
        sizeRef.current = currentSize;
        onResizeGetter()(currentSize, oldSize);
      }
    };

    // 初始化的时候更新一下值
    update();

    // 第一次enable之后绑定事件，组件不销毁不解绑
    if (!enableGetter()()) {
      return;
    }

    initRef.current = true;

    // 任何属性的变化或者元素
    addMutationHandler(update, {
      mutation: true,
      transition: true,
      animation: true,
      scroll: true,
      resize: true,
    });

    return () => {
      sizeRef.current = getDefaultElementSize();
      initRef.current = false;
      removeMutationHandler(update);
    };
  }, [initRef.current, enableGetter()(), elementGetter()]);

  return sizeRef.current;
}
