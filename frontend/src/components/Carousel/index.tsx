import React, { useState, useEffect, useRef, useMemo } from "react";
import styles from './style.module.scss';

export type SlideItem = {
  id: number;
  image: string;
  title: string;
  textColor?: string;
  description?: string;
}

export interface CarouselProps {
  /* 自定义组件容器宽度 */
  width?: number;
  /* 自定义组件容器高度 */
  height?: number;
  /* 数据源 */
  dataSource: SlideItem[];
  /* 过渡时间 */
  duration?: number;
  /* 是否自动切换 false 情况下，关闭指示器动画 */
  autoplay?: boolean;
  /* 默认当前选中 */
  defaultActive?: number;
}

export function Carousel(props: CarouselProps) {
  const { width, height, dataSource = [], duration = 3000, autoplay = false, defaultActive = 1 } = props;
  const slide = useRef<HTMLDivElement>(null);
  const [currentActive, setCurrentActive] = useState<number>(-1);
  const { width: slideWrapperWidth = 0 } = slide.current?.getBoundingClientRect() || {};

  // 解决首次渲染指示器过渡动画未执行问题
  useEffect(() => {
    setCurrentActive(defaultActive - 1)
  }, [defaultActive]);

  // 切换逻辑处理
  useEffect(() => {
    if (!autoplay) return;

    const interval = setInterval(() => {
      setCurrentActive(currentActive >= dataSource.length - 1 ? 0 : currentActive + 1);
    }, duration);

    return () => {
      clearInterval(interval);
    };
  }, [autoplay, currentActive, dataSource.length, duration]);

  // 计算需要滑动的距离
  const offset = useMemo(() => slideWrapperWidth * currentActive, [currentActive, slideWrapperWidth]);

  // 点击指示器手动切换
  const handleIndicatorClick = (index: number) => {
    setCurrentActive(index)
  };

  return (
    <div className={styles.wrapper} style={{ width: width, height: height }} ref={slide}>
      <div className={styles.slide_list} style={{ transform: `translateX(-${offset}px)` }}>
        {dataSource.map(item => (
          <div
            key={item.id}
            className={styles.slide_item}
            style={{ width: slideWrapperWidth, backgroundImage: `url(${item.image})` }}
          >
            <h3
              className={styles.slide_item_title}
              style={{ color: item.textColor }}
              dangerouslySetInnerHTML={{ __html: item.title || '' }}
            />
            <span
              className={styles.slide_item_description}
              style={{ color: item.textColor }}
              dangerouslySetInnerHTML={{ __html: item.description || '' }}
            />
          </div>
        ))}
      </div>
      <div className={styles.indicator}>
        {dataSource.map((item, index) => (
          <div
            key={item.id}
            className={styles.indicator_item}
            onClick={() => handleIndicatorClick(index)}
          >
            <div
              className={styles.indicator_fill}
              style={currentActive === index ? {
                width: '100%',
                transition: autoplay ? `width ${duration}ms linear` : ''
              } : {}}
            />
          </div>
        ))}
      </div>
    </div>
  )
}
