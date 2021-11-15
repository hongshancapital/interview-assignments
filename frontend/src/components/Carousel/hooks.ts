import {
  useEffect,
  useState,
  ReactChild,
  ReactFragment,
  ReactPortal,
  ReactNode,
  Children,
} from 'react';

export type childArray = (ReactChild | ReactFragment | ReactPortal)[];
// 整理children数据
function useSlideData(children: ReactNode) {
  const [slideData, setSlideData] = useState<childArray>([]);
  const [total, setTotal] = useState<number>(0);

  useEffect(() => {
    let childrenArray = Children.toArray(children);

    childrenArray = childrenArray.filter((child) => {
      if (typeof child === 'object') return !!child;
      if (typeof child === 'string') {
        throw new TypeError('不可传入字符串');
      }
      return !!child;
    });

    setSlideData(childrenArray);
  }, [children]);

  useEffect(() => {
    setTotal(slideData.length);
  }, [slideData]);

  return { slideData, total };
}

// 滑动
function useSlide(
  current: number,
  total: number,
  handleCurrent: (index: number) => void
) {
  const [tx, setTX] = useState(0);
  useEffect(() => {
    if (current > total) {
      handleCurrent(1);
    }

    setTX(current - 1);
  }, [current, total, handleCurrent]);

  return [tx];
}

export { useSlideData, useSlide };
