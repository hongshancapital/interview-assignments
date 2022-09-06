import { useState, useEffect, useMemo, useCallback } from "react";

interface useCarouselParam {
  // 默认其实位置
  defaultActiveIndex: number;
  // 图片切换时长（单位：ms）
  duration: number;
  // 子节点数量
  count: number;
}
export const useCarousel = ({
  defaultActiveIndex,
  duration,
  count,
}: useCarouselParam) => {
  // 计算初始化激活序号
  const initActiveIndex = useMemo(() => {
    if (defaultActiveIndex < 0) {
      return 0;
    }
    if (defaultActiveIndex >= count) {
      return count - 1;
    }
    return defaultActiveIndex;
  }, [defaultActiveIndex]);

  // 当前激活位置
  const [curIndex, setCurIndex] = useState<number>(initActiveIndex);

  // 跳转到指定步数
  const handleToStep = useCallback((index: number) => {
    setCurIndex(index);
  }, []);

  // 循环播放
  useEffect(() => {
    const timer = setTimeout(() => {
      const nextIndex = curIndex + 1 >= count ? 0 : curIndex + 1;
      setCurIndex(nextIndex);
    }, duration);

    return () => clearTimeout(timer);
  }, [curIndex, duration]);

  return { curIndex, handleToStep };
};
