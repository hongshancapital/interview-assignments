import React, { useState, useEffect } from "react";
import style from "./carousel.module.scss";
/**
 * @param {children} children ReactNode
 * @param {width} width 宽度
 * @param {height} height 高度
 * @param {styles} styles 样式
 * @returns 轮播图 单项
 */
export const CarouselItem = ({
  children = React.createElement("div"),
  width = "100%",
  height = "100%",
  styles = {},
}):JSX.Element => {
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
 * @param {title} title 标题
 * @param {text} text 副文本
 * @param {image} image 图片
 * @returns 轮播图 主体
 */

export const CarouselInfo = ({ title = "", text = "", image = "" }):JSX.Element => {
  return (
    <div className="carousel_info_container">
      <div className="carousel_info_info">
        <h1>{title}</h1>
        <span>{text}</span>
      </div>
      <div className="carousel_info_image_container">
        <img src={image} alt="这是图片" className="carousel_info_image" />
      </div>
    </div>
  );
};

/**
 * @param {children} children ReactNode
 * @param {switchingTime} switchingTime 间隔时间 默认3秒 以毫秒为单位 3000ms = 3s
 * @returns 轮播图 容器
 */
const Carousel:Function = ({
  children = React.createElement("div"),
  switchingTime = 3000,
}):JSX.Element => {
  const time = ((switchingTime % 60000) / 1000).toFixed(0); // 将毫秒转换为秒
  const [activeIndex, setActiveIndex] = useState(0); // 对应索引

  /**
   * 更新索引
   * @param {newIndex} newIndex 更新索引
   */
  const onUpdateIndex = (newIndex:number) => {
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
  const onClickCarouselIndex = (index:number) => {
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
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {React.Children.map(children, (child) => {
          return React.cloneElement(child, { width: "100%", height: "100vh" });
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
                  backgroundColor: index === activeIndex ? "#FFFFFF" : null,
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
