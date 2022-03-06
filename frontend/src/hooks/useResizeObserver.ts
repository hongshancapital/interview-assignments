import { useState, MutableRefObject, useLayoutEffect } from "react";

interface Size {
  width: number;
  height: number;
}

/**
 * 观察指定DOM的尺寸变化
 * @param targetRef
 * @returns
 */
export const useResizeObserver = (
  targetRef: MutableRefObject<Element | null>
) => {
  const [size, setSize] = useState<Size>();

  useLayoutEffect(() => {
    if (!targetRef.current) return;
    const resizeObserver = new ResizeObserver((entries) => {
      for (const entry of entries) {
        const { width, height } = entry.contentRect;
        setSize({ width, height });
      }
    });
    resizeObserver.observe(targetRef.current);
    return () => {
      resizeObserver.disconnect();
    };
  }, [targetRef]);

  return size;
};
