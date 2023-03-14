import React, {
  CSSProperties,
  isValidElement,
  ReactElement,
  useMemo,
} from "react";

import "./CarouselItem.scss";

export interface CarouselItemProps {
  count: number;
  interval: number;
  activeIndex: number;
  children: any;
  transitionTime: number;
}

const CarouselItem: React.FC<CarouselItemProps> = ({
  count, // 轮播数据项长度
  interval,
  activeIndex,
  children,
  transitionTime,
}) => {
  // 设置移动动画效果
  const setAnimationStyle = (): CSSProperties => {
    const left = -(100 * activeIndex);
    // 动画时间控制
    const animationTime = interval < transitionTime ? interval : transitionTime;
    return {
      width: `${100 * count}%`,
      left: `${left}%`,
      transition: `all ${animationTime / 1000}s`,
    };
  };

  // 缓存子DOM节点信息
  const carouselContent = useMemo(() => {
    const list: any = [];
    React.Children.forEach(children, (child) => {
      list.push(child);
    });
    return list;
  }, [children]);

  // 获取dom中key数据
  const getKey = (child: any, fallbackKey: string) =>
    (isValidElement(child) && child.key) || fallbackKey;

  // 生成子DOM节点
  const getChildDom = (children: any) => {
    let items: ReactElement[] = [];
    React.Children.forEach(children, (child, index) => {
      items.push(
        // child外层包裹一下
        React.cloneElement(<div>{child}</div>, {
          key: "carousel-item-" + getKey(child, String(index)),
          className: "carousel-item",
          "data-index": index,
        })
      );
    });
    return items;
  };

  return (
    <div className="wrap-content" style={setAnimationStyle()}>
      {getChildDom(carouselContent)}
    </div>
  );
};

export default CarouselItem;
