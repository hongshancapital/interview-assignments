import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { carouselPropsType } from './interface';
import styles from './index.module.css';
/**
 * @description: 轮播组件
 * @param {number} switchingTime 间隔时间，默认3s，以毫秒为单位3000ms=3s
 * @author: Zhao Min 曌敏
 */
const Carousel = ({
  children,
  switchingTime = 3000,
  indicatorColorList,
}: carouselPropsType) => {
  // 将毫秒转换为秒
  const time = useMemo(
    () => ((switchingTime % 60000) / 1000).toFixed(0),
    [switchingTime]
  );
  // 当前active项的index
  const [activeIndex, setActiveIndex] = useState(0);

  /**
   * @description: 重制动画
   * @author: Zhao Min 曌敏
   */
  const replayAnimations = useCallback(() => {
    document.getAnimations().forEach((animation) => {
      animation.cancel();
      animation.play();
    });
  }, []);

  /**
   * @description: 更新索引
   * @param {number} newIndex
   * @author: Zhao Min 曌敏
   */
  const onUpdateIndex = useCallback(
    (newIndex: number) => {
      let nextActiveIndex = newIndex;
      // corner case
      if (newIndex < 0) {
        nextActiveIndex = children.length - 1;
      } else if (newIndex >= children.length) {
        nextActiveIndex = 0;
      }
      setActiveIndex(nextActiveIndex);
      replayAnimations();
    },
    [replayAnimations, children.length]
  );
  /**
   * @description: 设置自动播放
   * @author: Zhao Min 曌敏
   */
  useEffect(() => {
    const timeInterval = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, switchingTime);

    return () => {
      timeInterval && clearInterval(timeInterval);
    };
  });

  /**
   * @description: 底部加载条点击事件
   * @param {number} newIndex 被点击的索引
   * @author: Zhao Min 曌敏
   */
  const onClickIndicator = useCallback(
    (newIndex: number) => {
      onUpdateIndex(newIndex);
      replayAnimations();
    },
    [onUpdateIndex, replayAnimations]
  );

  return (
    <div className={styles.body}>
      <div
        className={styles.content}
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {children}
      </div>
      <div className={styles.dots}>
        {React.Children.map(children, (child, index) => {
          const isActiveIndex: boolean = index === activeIndex;
          return (
            <div
              className={styles.indicatorOuter}
              style={{ backgroundColor: `${indicatorColorList[activeIndex]}` }}
              onClick={() => onClickIndicator(index)}
            >
              <div
                className={styles.indicatorInner}
                style={{
                  animationDuration: isActiveIndex ? `${time}s` : '0s',
                  backgroundColor: isActiveIndex
                    ? 'rgba(249,249,249,1)'
                    : undefined,
                }}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
