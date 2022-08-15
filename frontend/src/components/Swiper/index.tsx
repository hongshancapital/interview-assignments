import React, { FC, useEffect, useRef, useState } from "react";
import "./index.css";
export interface SwiperProps {
  /**
   * 轮播时间间隔，单位ms，默认3000
   */
  autoplayTimeout?: number;

  /**
   * 轮播子页，为了组件的复用性和通用性，可以是不同布局方式的子页
   */
  children?: React.ReactNode[] | React.ReactNode;

  /**
   * 根组件自定义样式
   */
  rootStyle?: React.CSSProperties;
  /**
   * 导航页码自定义样式
   */
  indicatorStyle?: React.CSSProperties;
  /**
   * 当前导航页码自定义样式
   */
  activeIndicatorStyle?: React.CSSProperties;
}

const Swiper: FC<SwiperProps> = ({
  children,
  autoplayTimeout = 3000,
  rootStyle,
  indicatorStyle,
  activeIndicatorStyle,
}) => {
  const isArray = Array.isArray(children);
  const views = isArray ? children : [children];

  const [curIndex, setCurIndex] = useState(0);

  const refView = useRef<HTMLDivElement>();

  useEffect(() => {
    const timer = setTimeout(() => {
      if (!refView.current) {
        return;
      }
      let index = curIndex === views.length - 1 ? 0 : curIndex + 1;
      const rootView = refView.current;
      const unit_width = rootView.getBoundingClientRect().width;
      rootView.scrollTo({
        left: unit_width * index,
        behavior: "smooth",
      });
      setCurIndex(index);
    }, autoplayTimeout);

    return () => {
      clearTimeout(timer);
    };
  }, [curIndex]);

  return (
    <div className={"swiper"} style={rootStyle}>
      <div
        className={"swiper-content"}
        ref={(ref) => {
          ref && (refView.current = ref);
        }}
      >
        {views.map((view, index) => {
          return (
            <div className="swiper-content-item" key={index}>
              {view}
            </div>
          );
        })}
      </div>
      <div className="swiper-indicator-root">
        {views.map((view, index) => {
          return (
            <div
              className="swiper-indicator-item"
              style={indicatorStyle}
              key={index}
            >
              {index === curIndex && (
                <div
                  className="swiper-indicator-item-active"
                  style={{
                    animationDuration: autoplayTimeout + "ms",
                    ...activeIndicatorStyle,
                  }}
                  key="active"
                />
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Swiper;
