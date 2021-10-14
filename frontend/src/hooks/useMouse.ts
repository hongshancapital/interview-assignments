import { useEffect } from 'react';

let mousePosition = { x: -10000, y: -10000 };

const getMousePosition = () => mousePosition;

interface Rect {
  left: number;
  right: number;
  top: number;
  bottom: number;
}

/**
 * 判断鼠标是否在矩形区域内
 * @param rect 
 * @param offset 精度阀值
 */
export function isMouseInRect(rect: Rect | HTMLElement, offset = 0) {
  const { x, y } = mousePosition;
  const { left, right, top, bottom } = rect instanceof HTMLElement ? rect.getBoundingClientRect() : rect;
  return (x >= right - offset || x <= left + offset || y <= top + offset || y >= bottom - offset) === false;
}

/**
 * 返回一个函数用于获取鼠标当前位置 x,y
 */
export const useMouse = () => {
  useEffect(() => {
    const handleMouseEvent = (evt: MouseEvent) => {
      const { x, y } = evt;
      mousePosition = { x, y };
    };
    document.addEventListener('mousemove', handleMouseEvent);
    return () => {
      document.addEventListener('mousemove', handleMouseEvent);
    };
  }, []);

  return { getMousePosition };
};
