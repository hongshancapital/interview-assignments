import React, { useState, useEffect, ReactNode, ReactElement, cloneElement ,useCallback} from "react";
import style from "./carousel.module.scss";
type CarouselItemProps = {
  children?: ReactNode;
  width?: string;
  height?: string;
  styles?: React.CSSProperties;
};
type CarouselProps = {
  children?: ReactNode;
  switchingTime?: number;
};
type UseCarouselProps = {
  children?: React.ReactNode;
  switchingTime?: number;
};
type UseCarouselReturnType = {
  activeIndex: number;
  time: string;
  onClickCarouselIndex: (index: number) => void;
};
/**
 * useCarousel hooks
 * @param {children} children ReactNode
 * @param {switchingTime} switchingTime 间隔时间 默认3秒 以毫秒为单位 3000ms = 3s
 * @returns
 */
export const useCarousel = ({
  children = <div />,
  switchingTime = 3000,
}: UseCarouselProps): UseCarouselReturnType => {
  const time = ((switchingTime % 60000) / 1000).toFixed(0); // 将毫秒转换为秒
  const [activeIndex, setActiveIndex] = useState<number>(0); // 对应索引

  /**
   * 更新索引
   * @param {newIndex} newIndex 更新索引
   */
  const onUpdateIndex = useCallback( (newIndex: number) => {
    if (newIndex < 0) {
      newIndex = React.Children.count(children) - 1;
    } else if (newIndex >= React.Children.count(children)) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    replayAnimations();
  },[ children ])

  /**
   * 重置动画
   */
  const replayAnimations = () => {
    document.getAnimations().forEach((anim) => {
      anim.cancel();
      anim.play();
    });
  };

  /**
   * 底部加载条点击事件
   * @param {index} index 跳转索引
   */
  const onClickCarouselIndex = (index: number) => {
    onUpdateIndex(index);
    replayAnimations();
  };

  useEffect(() => {
    const interval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, switchingTime);

    return () => {
      clearInterval(interval);
    };
  }, [activeIndex, onUpdateIndex, switchingTime]);

  return { activeIndex, time, onClickCarouselIndex };
};


/**
 * 轮播图单项
 */
export const CarouselItem = ({
  children = <div />,
  width = "100%",
  height = "100%",
  styles = {},
}: CarouselItemProps): ReactElement => {
  return (
    <div
      className={style.carousel_item}
      style={{ width: width, height: height, ...styles }}
    >
      {children}
    </div>
  );
};



/**
 * 轮播图容器
 */
const Carousel = ({
  children = <div />,
  switchingTime = 3000,
}: CarouselProps): ReactElement => {
  const { activeIndex, time, onClickCarouselIndex } = useCarousel({
    children,
    switchingTime,
  });

  return (
    <div className={style.container}>
      <div
        className={style.inner}
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {React.Children.map(children, (child) => {
          return cloneElement(child as ReactElement, { width: "100%", height: "100%" });
        })}
      </div>
      <div className={style.loading}>
        {React.Children.map(children, (child, index) => {
          return (
            <div
              className={style.indicator_outer}
              onClick={() => onClickCarouselIndex(index)}
            >
              <div
                className={style.indicator_inside}
                style={{
                  animationDuration: index === activeIndex ? `${time}s` : "0s",
                  backgroundColor: index === activeIndex ? "#FFFFFF" : undefined,
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
