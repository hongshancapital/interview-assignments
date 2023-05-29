import  {
  forwardRef, 
  ForwardRefRenderFunction,
  Children,
  PropsWithChildren,
  useMemo,
  useRef,
  useImperativeHandle,
  useState,
  useEffect,
  useCallback,
} from 'react';
import classnames from 'classnames/bind';
import useResize from '../../hooks/useResize';
import styles from './index.module.css'

export const cn = classnames.bind(styles);

export interface CarouselRef {
  goNext: () => void; // 切换到上一面板
  goPrev: () => void; // 切换到下一面板
  goTo: (index: number) => void; // 切换到指定面板
}

export interface CarouselProps {
  className?: string;
  defaultIndex?: number; // 初始化播放的元素序号
  gap?: number; // 切换的间隔时间
  autoplay?: boolean; // 是否自动播放
  afterChange?: (args: number) => void; // 切换后的回调
}

const Carousel: ForwardRefRenderFunction<CarouselRef, PropsWithChildren<CarouselProps>> = ({
  children,
  className,
  gap = 2000,
  autoplay = true,
  defaultIndex = 0,
  afterChange,
 }, ref) => {

  const [currentIndex, setCurrentIndex] = useState(defaultIndex);
  const timerRef = useRef<NodeJS.Timeout>();
  const wrapperRef = useRef<HTMLDivElement>(null);
  const [carouselWidth, setCarouselWidth] = useState<number>(0);

  /* 处理屏幕大小变化 */
  useResize(setCarouselWidth, wrapperRef)

  const clearTimer = useCallback(() => {
    timerRef.current && clearTimeout(timerRef.current);
  }, []);

  /* 跳转指定页 */
  const goTo = useCallback((index: number) => {
    clearTimer();
    setCurrentIndex(index);
  }, [clearTimer]);

  /* 获取children的个数 */
  const count = useMemo(() => {
    return Children.count(children);
  }, [children]);

  /* 跳转下一个 */
  const goNext = useCallback(() => {
    clearTimer();
    setCurrentIndex(currentIndex === count - 1 ? 0 : currentIndex + 1);
    
  }, [currentIndex, count, clearTimer]);

  /* 跳转上一个 */
  const goPrev = useCallback(() => {
    clearTimer();
    setCurrentIndex( currentIndex === 0 ? count - 1 : currentIndex - 1);
  }, [currentIndex, count, clearTimer]);

  useEffect(() => {
    if (autoplay) {
      timerRef.current = setTimeout(() => {
        goNext();
      }, gap);
    }
    return () => {
      clearTimer();
    }
  }, [autoplay, clearTimer, currentIndex, gap, goNext]);

   // 获取Carousel组件的宽度
  //  useLayoutEffect(() => {
  //   console.log('1111 wrapperRef.current.offsetWidth', wrapperRef.current?.offsetWidth)
  //   wrapperRef.current && setCarouselWidth(wrapperRef.current.offsetWidth)
  // }, []);

  useEffect(() => {
    afterChange?.(currentIndex);
  }, [afterChange, currentIndex]);
  
  /* 暴露给外部的方法 */
  useImperativeHandle(
    ref,
    () => ({
      goTo,
      goPrev,
      goNext,
    }),
    [goTo, goPrev, goNext],
  );

  /**
   * 动态计算list的样式
   */
  const getListStyle = useMemo(() => {
    return {
      width: carouselWidth * count,
      transform: `translate3d(${-1 * currentIndex * carouselWidth}px, 0, 0)`,
    };
  }, [carouselWidth, count, currentIndex]);

  /* 轮播list */
  const createCarouselList = useMemo(() => {
    return Children.map(children, (child, index) => (
      <div
        key={index}
        className={cn(
          'carousel-item', 
          { 'carousel-item-active': index === currentIndex }
        )}
      >
        {child}
      </div>
    ))
  }, [children, currentIndex]);

  /* 轮播dot */
  const createDots = useMemo(() => {
    return (
      <div className={cn('dot-container')}>
        {
          new Array(count).fill('').map((_, index) => (
            <div 
              key={`dot-${index}`}
              className={cn('dot', { 'dot-active': index === currentIndex })}
              onClick={() => goTo(index)}
            >
              <div
                className={cn('dot-inner')}
                style={{transitionDuration: (index === currentIndex && autoplay) ? `${gap}ms` : undefined}}
              />
            </div>
          ))
        }
      </div>
    )
  }, [autoplay, count, currentIndex, gap, goTo]);

  return (
    <div className={`${cn('carousel')} ${className}`} ref={wrapperRef}>
      <div
        className={cn('carousel-list', 'auto-play')}
        style={getListStyle}
      >
        {createCarouselList}
      </div>
      {createDots}
    </div>
  )
};
export default forwardRef(Carousel);
