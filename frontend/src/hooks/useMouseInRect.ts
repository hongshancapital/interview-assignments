import { useEffect, useState, useRef, useCallback } from 'react';

/**
 * 判断鼠标是否在指定dom矩形区域内
 */
export const useMouseInRect = () => {
  const [mouseInRect, setMouseInRect] = useState(false);

  const rectRef = useRef<HTMLElement>();

  const handleMouseMove = useCallback(
    (evt: MouseEvent) => {
      const target = rectRef.current;
      if (!target) return;
      
      const { x, y } = evt;
      const { left, right, top, bottom } = target.getBoundingClientRect();
      if (x > right || x < left || y < top || y > bottom) {
        setMouseInRect(false);
      } else {
        setMouseInRect(true);
      }
    },
    [rectRef]
  );

  useEffect(() => {
    document.addEventListener('mousemove', handleMouseMove);
    return () => {
      document.removeEventListener('mousemove', handleMouseMove);
    };
  }, [handleMouseMove]);

  return [rectRef, mouseInRect];
};
