import React, { useState, useEffect } from "react";
import style from "./carousel.module.css";

interface CarouseItem {
  children?: React.ReactNode,
  width?: string,
  height?: string,
  styles?: React.CSSProperties
  key?: number | undefined
}
export const CarouselItem: React.FC<CarouseItem> = ({
  children = React.createElement("div"),
  width = "100%",
  height = "100%",
  styles = {}
}) => {
  return (
    <div
      className={style.carousel_item}
      style={{ width: width, height: height, ...styles }}
    >
      {children}
    </div>
  );
};
interface CommentProps {
  title?: string
  describe?: string
  image?: string,
  color?: string
}
export const CarouselInfo: React.FC<CommentProps> = ({ title = "", describe = "", image = "", color = ""}, ref: any) => {
  return (
    <div className={style.carousel_info_container}>
      <div className={style.carousel_info_info} style={{color}}>
        <h1 className={style.title}>{title}</h1>
        <span className={style.describe}>{describe}</span>
      </div>
      <div className={style.carousel_info_image_container}>
        <img src={image} alt="" className={style.carousel_info_image} style={{width: '100%', height:"100%"}}/>
      </div>
    </div>
  );
};

interface CarouselProps {
  children?: React.ReactNode
  switchingTime?: number
  styles?: React.CSSProperties
}
const Carousel: React.FC<CarouselProps> = ({
  children = React.createElement("div"),
  switchingTime = 3000,
  styles = {}
}, ref) => {
  const time = ((switchingTime % 60000) / 1000).toFixed(0); // 将毫秒转换为秒
  const [activeIndex, setActiveIndex] = useState(0); // 对应索引

  /**
   * 更新索引
   * @param {newIndex} newIndex 更新索引
   */
  const onUpdateIndex = (newIndex: number) => {
    if (newIndex < 0) {
      newIndex = React.Children.count(children) - 1;
    } else if (newIndex >= React.Children.count(children)) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    replayAnimations();
  };

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
      if (interval) {
        clearInterval(interval);
      }
    };
  });

  return (
    <div className={style.container}>
      <div
        className={style.inner}
        style={{ transform: `translateX(-${activeIndex * 100}%)`, position: 'relative' }}
      >
        {React.Children.map(children, (child: any) => {
          return React.cloneElement(child, { width: "100vw", height: "100vh" });
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
                  backgroundColor: index === activeIndex ? "#FFFFFF" : "",
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

