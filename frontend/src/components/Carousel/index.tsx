/* eslint-disable react-hooks/exhaustive-deps */
// /* eslint-disable react-hooks/exhaustive-deps */
import React, {
  useState,
  useMemo,
  useRef,
  useEffect,
  forwardRef,
  Children,
  isValidElement,
  useImperativeHandle,
  Fragment,
} from "react";
import {
  CSSProperties,
  PropsWithChildren,
  Ref,
  Dispatch,
  SetStateAction,
} from "react";

import "./index.css";

/** 列表相关 style */
interface IStyles {
  listStyles: CSSProperties; // list style
  listItemStyles: CSSProperties; // list item style
}

interface IProps extends PropsWithChildren {
  interval?: number; // 轮播间隔
  duration?: number; // 过渡时间
}

export interface CarouselRef {
  activeIndex: number;
  setActiveIndex: Dispatch<SetStateAction<number>>;
}

const Carousel = forwardRef((props: IProps, ref: Ref<CarouselRef>) => {
  const timerRef = useRef<NodeJS.Timer>();
  const { duration = 1, interval = 3000, children = [] } = props || {};
  // 转化成数组，且自动过滤了 boolean | null | undefined 类型节点
  const newChildren = useMemo(() => Children.toArray(children), [children]);
  const childrenCount = newChildren.length;
  const showDot = childrenCount > 1; // 大于 1 个时才展示 dot

  const [activeIndex, setActiveIndex] = useState<number>(-1);

  // 对外提供 activeIndex、setActiveIndex
  useImperativeHandle(ref, () => ({
    activeIndex,
    setActiveIndex,
  }));

  const play = () => {
    timerRef.current && clearTimeout(timerRef.current);
    // interval 间隔后 activeIndex 自动 +1
    timerRef.current = setTimeout(() => {
      setActiveIndex((preIndex) => {
        return preIndex === childrenCount - 1 ? 0 : preIndex + 1;
      });
    }, interval);
  };

  // activeIndex 驱动动画
  useEffect(() => {
    showDot && play();
  }, [activeIndex]);

  // props 上相关值更新时，重置 activeIndex
  useEffect(() => {
    // activeIndex 本来就是  0 时，不会触发 activeIndex 的更新，需要手动重新 play
    if (activeIndex === 0) {
      showDot && play();
    }
    setActiveIndex(0);
  }, [newChildren, interval]);

  useEffect(() => {
    return () => {
      timerRef.current && clearTimeout(timerRef.current);
    };
  }, []);

  const styles = useMemo<IStyles>(() => {
    const itemWidth = (1 / childrenCount) * 100;
    return {
      listStyles: {
        width: `${childrenCount * 100}%`, // list wrapper 宽度 = 子项个数 * 100%
        transition: `transform ${duration}s`,
        transform:
          activeIndex >= 0 ? `translateX(${-itemWidth * activeIndex}%)` : "",
      },
      listItemStyles: {
        width: `${itemWidth}%`,
      },
    };
  }, [duration, activeIndex, childrenCount]);

  // dot 的进度条样式
  const dotProgressStyle: CSSProperties = {
    width: "0",
    animationDuration: `${interval}ms`,
    animationTimingFunction: "linear",
  };

  if (!newChildren.length) return null;

  return (
    <section className="carousel-container">
      <div className="slider-list" style={styles.listStyles}>
        {newChildren.map((child, index) => {
          // 1. 针对 dom 元素，直接返回对应 dom，把 extProps 注入到 dom 节点上，避免丢失 dom 上配好的 key
          if (isValidElement(child) && typeof child.type === "string") {
            const { className = "", style = {} } = child.props;
            const extProps = {
              className: `${className} slider-list-item`,
              style: { ...style, ...styles.listItemStyles },
            };
            return React.cloneElement(child, extProps);
          }

          // 2. 其他类型，组件、Fragment、string、number 等，额外创建一个 div 包裹，给创建的 div 注入 extProps
          return (
            <div
              key={
                isValidElement(child) && child.type !== Fragment
                  ? child.key
                  : null
              }
              className="slider-list-item"
              style={styles.listItemStyles}
            >
              {child}
            </div>
          );
        })}
      </div>

      {showDot && (
        <div className="slider-dots">
          {newChildren.map((child, index) => {
            return (
              <div
                className="slider-dot-item"
                key={index}
                onClick={() => {
                  setActiveIndex(index);
                }}
              >
                <div
                  className="slider-dot-progress"
                  style={{
                    ...dotProgressStyle,
                    animationName: index === activeIndex ? "progress" : "",
                  }}
                />
              </div>
            );
          })}
        </div>
      )}
    </section>
  );
});

export default Carousel;
