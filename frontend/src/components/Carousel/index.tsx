import React, {
  ReactNode,
  useState,
  useRef,
  useEffect,
  useLayoutEffect,
  TouchEvent,
} from 'react';
import './style.css';

export interface CarouselProps {
  // 当前所在滑块的 index 默认: 0
  // current?: number;
  // 滑动动画时长 默认: 500
  duration?: number;
  // 自动切换时间间隔 默认: 5000
  interval?: number;
  // 是否自动切换 默认: true
  autoplay?: boolean;
  // 滑块列表
  children: ReactNode[];
  // 滑块变动事件
  onChange?: (index: number) => void;
}

const Carousel = ({
  duration = 500,
  interval = 5000,
  autoplay = true,
  children,
  onChange,
}: CarouselProps) => {
  const [current, setCurrent] = useState(0);
  const [offset, setOffset] = useState(0);
  const [carouselWidth, setCarouselWidth] = useState(0);
  const carouselRef: any = useRef({ index: 0, timer: null });
  const [touchX, setTouchX] = useState(0);
  const itemsLength = children.length;

  const swiperTo = (index: number) => {
    setOffset(index * carouselWidth);
    setCurrent(index);
    // 更新ref里的index, 给监听事件使用
    carouselRef.current.index = index;
    // 抛出onChange事件
    if (onChange) onChange(index);
  };

  const getPositionNextIndex = (nextIndex: number) => {
    return nextIndex >= itemsLength ? 0 : nextIndex;
  };

  const doAutoplay = (nextCurrent: number = current) => {
    if (carouselRef.current.timer) clearTimeout(carouselRef.current.timer);
    const nextIndex: number = getPositionNextIndex(nextCurrent + 1);

    carouselRef.current.timer = setTimeout(() => {
      swiperTo(nextIndex);
      doAutoplay(nextIndex);
    }, interval);
  };

  const doTouch = (diffX: number) => {
    if (carouselRef.current.timer) clearTimeout(carouselRef.current.timer);
    const limit = itemsLength - 1
    let nextOffset = offset + diffX;

    if (nextOffset < 0) nextOffset = 0;
    if (nextOffset >= limit * carouselWidth) {
      nextOffset = limit * carouselWidth;
    }

    setOffset(nextOffset);
  };

  // 记录开始触摸的时机
  const touchStart = (el: TouchEvent) => {
    setTouchX(el.nativeEvent.touches[0].clientX);
  };

  // 触摸过程，实时滑动更新位置
  const touchMove = (el: TouchEvent) => {
    const diffX = touchX - el.nativeEvent.touches[0].clientX;
    setTouchX(el.nativeEvent.touches[0].clientX);

    doTouch(diffX);
  };

  // 触摸结束时间
  // 触摸结束，结算下一个滑块
  const touchEnd = () => {
    const endPosition = offset / carouselWidth;
    const endPartial = endPosition % 1;
    const endingIndex = endPosition - endPartial;
    const deltaInteger = endingIndex - current;

    let nextIndex = endingIndex;

    // if (nextIndex >= itemsLength) return

    if (deltaInteger >= 0) {
      if (endPartial >= 0.1) nextIndex += 1;
    } else if (deltaInteger < 0) {
      nextIndex = current - Math.abs(deltaInteger);
      if (endPartial > 0.9) nextIndex += 1;
    }

    swiperTo(nextIndex);
    // 重新调起自动播放
    if (autoplay) doAutoplay(getPositionNextIndex(nextIndex));
  };

  // 获取容器的宽度
  const getContainerWidth = () => {
    const width: number =
      document.querySelector('.carousel-container')?.clientWidth ?? 0;
    setCarouselWidth(width);
    // 窗口变化后，滑块滚动位置矫正
    if (carouselRef.current.index > 0)
      setOffset(carouselRef.current.index * width);
  };

  // 拿到初始组件宽度
  useLayoutEffect(() => {
    getContainerWidth();
    // 监听窗口宽度变化
    window.addEventListener('resize', getContainerWidth);
    return () => {
      window.removeEventListener('resize', getContainerWidth);
    };
  }, []);

  // 是否开启自动播放
  useEffect(() => {
    if (carouselWidth && autoplay) doAutoplay();
  }, [carouselWidth, autoplay]);

  return (
    <div
      className="carousel-container"
      onTouchStart={touchStart}
      onTouchMove={touchMove}
      onTouchEnd={touchEnd}
    >
      {/* 滑块 */}
      <div
        className="carousel-items"
        style={{
          transform: `translateX(${offset * -1}px)`,
          transitionDuration: `${duration}ms`,
        }}
      >
        {children}
      </div>
      {/* 进度条 */}
      <div className="indicators">
        {children.map((_, i) => (
          <div key={i} className="indicator">
            <div
              className={`progress ${autoplay ? '' : 'active'} ${
                current === i && autoplay ? 'animate' : ''
              }`}
              style={{ animationDuration: `${interval}ms` }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
