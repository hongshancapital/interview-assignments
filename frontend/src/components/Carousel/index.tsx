import { 
  forwardRef, 
  ForwardRefRenderFunction, 
  useImperativeHandle, 
  ReactNode, 
  Children, 
  useRef, 
  useState, 
  useEffect, 
  useMemo, 
  useCallback, 
  useLayoutEffect 
} from 'react';

import './index.css';

export interface CarouselProps {
  children?: ReactNode;
  autoplay?: boolean;
  autoplayInterval?: number;
  waitForAnimate?: boolean;
}

export interface CarouselRef {
  next: () => void;
  prev: () => void;
  goTo: (index: number) => void;
}

const Carousel: ForwardRefRenderFunction<CarouselRef, CarouselProps> = ({
  children,
  autoplay=true,
  autoplayInterval=3000,
}, carouselRef) => {
  
  const [updateState, setUpdateState] = useState<number>(0);
  const [currentIndex, setCurrentIndex] = useState<number>(-1);

  const transformDurationRef = useRef<number>(500);

  const timerRef = useRef<number>();

  const wrapperRef = useRef<HTMLDivElement>(null);
  
  const [carouselContainerWidth, setCarouselContainerWidth] = useState<number>(0);

  // 获取Carousel组件的宽度
  useLayoutEffect(() => {
    wrapperRef.current && setCarouselContainerWidth(wrapperRef.current.offsetWidth)
  }, []);

  // 通过ResizeObserver监听组件变化并更新宽度
  useLayoutEffect(() => {
    let observerRefValue: HTMLDivElement | null = null;
    const resizeObserverCallback = (entries: ResizeObserverEntry[]) => {
      for (const entry of entries) {
        if (entry.contentBoxSize) {
          const inlineSize = entry.contentBoxSize.at(0)?.inlineSize;
          if (inlineSize !== undefined) {
            setCarouselContainerWidth(inlineSize)
          }
        }
      }
    }
    const resizeObserver = new window.ResizeObserver(resizeObserverCallback);
    if (wrapperRef.current) {
      resizeObserver.observe(wrapperRef.current);
      observerRefValue = wrapperRef.current
    }
    return () => {
      observerRefValue && resizeObserver.unobserve(observerRefValue)
    }
  }, []);

  // 获取children的count
  const itemCount = useMemo(() => {
    return Children.count(children);
  }, [children]);

  // 当触发点击事件或触发外部切换时更新该state，用来清除掉timer从新计时
  const update = useCallback(() => {
    setUpdateState(old => old+1);
  }, []);

  // 监听children变化，重置index
  useEffect(() => {
    setCurrentIndex(0);
    update();
  }, [children, update])

  const activeIndex = useCallback((index: number) => {
    setCurrentIndex((oldIndex) => {
      if (index !== oldIndex) {
        update();
      }
      return index;
    })
  }, [update]);

  useImperativeHandle(carouselRef, () => {
    return {
      next: () => {
        activeIndex((currentIndex + itemCount + 1) % itemCount);
      },
      prev: () => {
        activeIndex((currentIndex + itemCount - 1) % itemCount);
      },
      goTo: (index: number) => {
        activeIndex((index + itemCount) % itemCount);
      },
    }
  }, [currentIndex, itemCount, activeIndex]);

  useEffect(() => {
    const _run = () => {
      clearTimeout(timerRef.current);
      if (autoplay && itemCount > 1) {
        timerRef.current = window.setTimeout(() => {
          setCurrentIndex((oldIndex) => {
            return (oldIndex+1) % itemCount;
          });

          if (autoplay && timerRef.current !== undefined && itemCount > 1) {
            _run();
          }
        }, autoplayInterval)
      }
    }

    if (autoplay && itemCount > 1) {
      _run();
    }

    return () => {
      clearTimeout(timerRef.current);
      timerRef.current = undefined;
    }
  }, [autoplayInterval, autoplay, itemCount, updateState]);

  return (
    <div 
      ref={wrapperRef}
      className='carousel-container'
    >
      <div className='carousel-list'
        style={{
          width: carouselContainerWidth * itemCount,
          transform: `translateX(${-1 * currentIndex * carouselContainerWidth}px)`,
          transitionDuration: `${transformDurationRef.current}ms`
        }}
      >
        {
          Children.map(children, (child, index) => (
            <div key={index} className='carousel-item' style={{width: carouselContainerWidth}}>{child}</div>
          ))
        }
      </div>
      <div className='dot-container'>
        {
          new Array(itemCount).fill(undefined).map((_, index) => (
            <div 
              onClick={() => activeIndex(index)} 
              key={index} 
              className={`dot ${index === currentIndex ? 'dot-active': ''}`}
            >
              <div style={{
                transitionDuration: (index === currentIndex && autoplay) ? `${autoplayInterval}ms` : undefined,
              }}/>
            </div>
          ))
        }
      </div>
    </div>
  )
}

export default forwardRef(Carousel);
