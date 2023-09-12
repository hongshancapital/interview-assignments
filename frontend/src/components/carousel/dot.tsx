import React, { FC, useEffect, useRef, useMemo, useCallback } from "react";

interface DotProps {
  autoplay?: boolean; // 继承自父组件的是否自动
  autoplayWaitTime?: number; // 继承自父组件的是否自动等待时间
  index: number; // 当前dot在list中的位置
  currentIndex: number; // 当前被选中的dot，用来判断选中状态
  onSelect: (index: number) => void; // 变更选中的方法
}

const Dot: FC<DotProps> = React.memo(({ ...props }) => {
  const {
    onSelect,
    autoplay,
    autoplayWaitTime = 3000,
    index,
    currentIndex,
  } = props;
  const bannerRef = useRef<HTMLDivElement | null>(null);
  const indexRef = useRef<number>(index);
  const onSelectRef = useRef<(index: number) => void>(onSelect);
  const onSelectCallback = useCallback(
    () => onSelect(index),
    [onSelect, index]
  );
  // 根据是否自动和是否是当前的dot来计算出相应的style
  const style: React.CSSProperties = useMemo(() => {
    const style: React.CSSProperties = {};
    if (index === currentIndex) {
      if (autoplay) {
        style.animation = `carousel-auto-banner ${autoplayWaitTime}ms ease-out`;
      } else {
        style.width = "100%";
      }
    } else {
      style.width = 0;
    }
    return style;
  }, [autoplayWaitTime, currentIndex, autoplay]);
  // 由于onAnimationend中的index数值不会再更新了用一个ref来携带最新的index
  useEffect(() => {
    indexRef.current = index;
  }, [index]);
  useEffect(() => {
    onSelectRef.current = onSelect;
  }, [onSelect]);
  useEffect(() => {
    const listenerTransitionend = () => {
      const index = indexRef.current || 0;
      const onSelect = onSelectRef.current;
      onSelect(index + 1);
    };
    bannerRef.current &&
      bannerRef.current.addEventListener("animationend", listenerTransitionend);
    return () => {
      bannerRef.current &&
        bannerRef.current.removeEventListener(
          "animationend",
          listenerTransitionend
        );
    };
  }, []);

  return (
    <li className="dot-li" onClick={onSelectCallback}>
      <div className="dot">
        <div ref={bannerRef} style={style} className="dot-banner"></div>
      </div>
    </li>
  );
});

export default Dot;
