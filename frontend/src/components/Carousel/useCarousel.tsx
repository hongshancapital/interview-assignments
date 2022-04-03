import { useState, useRef, useEffect } from "react";

interface IOptions<T> {
  /**
   * 轮播项
   */
  items: T[];
  /**
   * 轮播周期间隔，单位 ms
   */
  ms: number;
  /**
   * 轮播的默认激活的下标
   * @default 0
   */
  defaultActiveIndex?: number;
}

interface IUseCarousel<T = number> {
  (options: IOptions<T>): T;
}

/**
 * 按一定间隔（ms）轮播传入的 items，返回当前轮次的 item
 */
const useCarousel: IUseCarousel = (options) => {
  const { items, ms, defaultActiveIndex = 0 } = options;
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  const timer = useRef<NodeJS.Timer>();

  useEffect(() => {
    let index = defaultActiveIndex;
    timer.current = setInterval(() => {
      index = (index + 1) % items.length;
      setActiveIndex(index);
    }, ms);

    return () => {
      timer.current && clearInterval(timer.current);
    };
  }, [items.length, ms, defaultActiveIndex]);

  return items[activeIndex];
};

export default useCarousel;
