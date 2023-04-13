import React, { useState, useEffect, useRef } from "react";
import styles from "./Carousel.module.scss";

// 定义 CarouselProps 类型
interface CarouselProps {
  images: { url: string; text: any }[];
  interval?: number;
}

const Carousel: React.FC<CarouselProps> = ({ images, interval = 3000 }) => {
  // 使用 useState 维护当前活动图片索引
  const [activeIndex, setActiveIndex] = useState(0);
  // 使用 useRef 创建进度条的引用
  const progressBarRefs = useRef(
    images.map(() => React.createRef<HTMLDivElement>())
  );
  // 使用 useRef 保存动画帧的引用
  const animationRef = useRef<number>();

  useEffect(() => {
    const progressBarRef = progressBarRefs.current[activeIndex];
    const animationStartTime = performance.now();

    // 定义 animate 函数，用于更新进度条宽度
    const animate = (timestamp: number) => {
      if (!progressBarRef.current) return;

      const progress = Math.min((timestamp - animationStartTime) / interval, 1);
      progressBarRef.current.style.width = `${progress * 100}%`;

      if (progress < 1) {
        animationRef.current = requestAnimationFrame(animate);
      } else {
        progressBarRef.current.style.width = "0%";
        setActiveIndex((prevIndex) => (prevIndex + 1) % images.length);
      }
    };

    animationRef.current = requestAnimationFrame(animate);

    // 在组件卸载或者属性更新时，取消上一个动画帧
    return () => {
      if (animationRef.current) {
        cancelAnimationFrame(animationRef.current);
      }
    };
  }, [activeIndex, images.length, interval]);

  // 定义指示器点击处理函数
  const handleIndicatorClick = (index: number) => {
    setActiveIndex(index);
  };

  // 渲染图片轮播的函数
  const renderSlides = () => {
    return images.map((image, index) => (
      <div
        key={index}
        style={{ backgroundImage: `url(${image.url}),url(${image.url})` }}
        className={`${styles.slide} ${
          index === activeIndex ? styles.active : styles.inactive
        }`}
      >
        {index === activeIndex && (
          <div className={`${styles.activeText} ${styles.slideText}`}>{image.text}</div>
        )}
      </div>
    ));
  };

  // 渲染指示器的函数
  const renderIndicators = () => {
    return images.map((_, index) => (
      <div
        key={index}
        data-testid="indicator"
        className={`${styles.indicator} ${
          index === activeIndex ? styles.active : ""
        }`}
        onClick={() => handleIndicatorClick(index)}
      >
        <div ref={progressBarRefs.current[index]} className={styles.progress} />
      </div>
    ));
  };

  // 返回 Carousel 组件的 JSX 结构
  return (
    <div className={styles.carousel} data-testid="carousel">
      <div className={styles.carouselSlides}>{renderSlides()}</div>
      <div className={styles.carouselIndicators}>{renderIndicators()}</div>
    </div>
  );
};

export default Carousel;
