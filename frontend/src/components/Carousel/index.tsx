import { useCallback, useEffect, useState, useImperativeHandle, forwardRef, Ref } from "react";

import { sleep } from '../../utils/time';
import './index.css';

export interface CarouselProps {
  autoPlay?: boolean;
  carouselItems: (() => JSX.Element)[];
  delay?: number;
  speed?: number;
  width?: string | number;
  height?: string | number;
  // playMode 播放模式，轮播组件一般是一直向左播放（循环），如果有需求可加上该模式
}

export interface goToConfig {
  skipSpeed?: boolean;
}

export interface CarouselImperative {
  next: () => Promise<void>;
  goTo: (nextIndex: number, {  skipSpeed }?: goToConfig) => Promise<void>;
  getCurrent: () => number;
}

const Carousel = ({
  carouselItems,
  delay = 2000,
  speed = 500,
  autoPlay = true,
  width = '100%',
  height = '100%',
}: CarouselProps, ref: Ref<CarouselImperative> | undefined) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  // 动画是否运行中
  const [isAnimate, setIsAnimate] = useState(true);

  const goTo = useCallback(async (nextIndex: number, {
    skipSpeed = false,
  }: goToConfig = {}) => {
    setCurrentIndex(nextIndex);

    if (!skipSpeed) {
      setIsAnimate(true);
      await sleep(speed);
    }
    setIsAnimate(false);
  }, [speed]);

  const next = useCallback(() => goTo((currentIndex + 1) % carouselItems.length), [currentIndex, carouselItems.length, goTo]);

  useEffect(() => {
    let interval: NodeJS.Timer | undefined;
    if (autoPlay) {
      interval = setInterval(() => {
        if (carouselItems.length === 0) {
          return;
        }
        next();
      }, delay + speed);
    }

    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  }, [delay, speed, goTo, carouselItems.length, autoPlay, next]);

  useEffect(() => {
    // 保证初次加载时，首次动画能正常展示
    setIsAnimate(false);
  }, []);

  useImperativeHandle(ref, () => ({
    next: next,
    goTo: goTo,
    getCurrent: () => currentIndex,
  }));

  return <div className="carousel" style={{
    width,
    height,
  }}>

    <div className="carousel-items" style={{
      transition: `left ${speed}ms linear`,
      left: `-${currentIndex * 100}%`,
    }}>
      {
        // 后续性能优化可隐藏不展示且下一个动画也不展示的组件
        carouselItems.map((renderItem, index) => <div key={index} className="carousel-item-container">{renderItem()}</div>)
      }
    </div>

    <div className="indicator">
      {
        carouselItems.map((_item, index) =>
          // 动画过程中，保持关闭状态
          <div key={index} className="indicator-item">
            <div className={`indicator-item-percent ${currentIndex === index && !isAnimate ? 'indicator-item-active' : ''}`} style={{
              transition: currentIndex === index ? `width ${delay}ms linear` : 'none',
            }}></div>
          </div>)
      }
    </div>
  </div>
};

export default forwardRef(Carousel);