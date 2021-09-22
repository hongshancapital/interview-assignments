import { useEffect, useRef } from 'react';
import { useRefGetter } from '../hooks';

import {
  IElementSize,
  addMutationHandler,
  getDefaultElementSize,
  getWindowSize,
  isElementSizeEqual,
  removeMutationHandler,
} from '../util';

export type ResizeHandler = (size: IElementSize, oldSize: IElementSize) => void;

export function useWindowSize(
  enable: () => boolean,
  onResize: ResizeHandler,
): IElementSize {
  const sizeRef = useRef(getDefaultElementSize());
  const enableGetter = useRefGetter(enable);
  const onResizeGetter = useRefGetter(onResize);

  // 这地方添加监听函数
  useEffect(() => {
    // 更新函数
    const update = (): void => {
      if (!enableGetter()()) {
        return;
      }

      const currentSize = getWindowSize();
      const oldSize = sizeRef.current;

      if (!isElementSizeEqual(sizeRef.current, currentSize)) {
        const handler = onResizeGetter();

        sizeRef.current = currentSize;
        handler && handler(currentSize, oldSize);
      }
    };

    update();

    addMutationHandler(update, {
      resize: true,
    });

    return () => removeMutationHandler(update);
  }, []);

  return sizeRef.current;
}
