import React, { CSSProperties } from "react";
import { throttle } from "../../utils/common";
import "./Dots.scss";

export interface DotsProps {
  count: number;
  interval: number;
  trigger?: string;
  activeIndex: number;
  setActiveIndex: Function;
}

const Dots: React.FC<DotsProps> = ({
  count, // 轮播数据项长度
  interval,
  trigger, // 触发方式：click or hover
  activeIndex,
  setActiveIndex,
}) => {
  // 面板指示点样式宽度动态分配计算,实现自适应宽度
  const setDotsSpanStyle = (total: number): CSSProperties => {
    return {
      width: total ? `calc(100%/${total})` : 0,
    };
  };

  // 面板指示点进度条动画效果
  const setDotsAnimation = (): CSSProperties => {
    return {
      width: "100%",
      // 位移移动过渡时间计算
      transition: `all ${interval / 1000}s`,
    };
  };

  // 面板指示点的点击事件
  const handleDotsClick = (index: number): void => {
    setActiveIndex(index);
  };
  // 面板指示点的鼠标经过事件
  const handleDotsHover = (index: number): void => {
    if (trigger === "hover" && index !== activeIndex) {
      setActiveIndex(index);
    }
  };
  // 节流性能优化: 面板指示点的鼠标经过事件
  const throttledDotsHover = throttle((index: number): void => {
    handleDotsHover(index);
  }, 300);

  // 根据数据长度生成面板指示点DOM节点
  const getDotsDom = (len: number, activeIndex: number): any[] => {
    const content = [];
    for (let i = 0; i < len; i++) {
      const isActive = activeIndex === i;
      content.push(
        <span
          className={isActive ? "active" : ""}
          style={setDotsSpanStyle(count)}
          key={`dots-span-${i}`}
          onMouseEnter={() => throttledDotsHover(i)}
          onClick={() => handleDotsClick(i)}
        >
          <i style={isActive ? setDotsAnimation() : {}}></i>
        </span>
      );
    }
    return content;
  };

  return <div className="dots">{getDotsDom(count, activeIndex)}</div>;
};

export default Dots;
