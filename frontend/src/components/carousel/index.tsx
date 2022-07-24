import React, {
  memo,
  FC,
  ReactNode,
  Children,
  useMemo,
  useRef,
  useState,
  useEffect,
  useCallback,
} from 'react';
import { useSize } from 'ahooks';
import style from './index.module.scss';
import Indicators, { SelectFunction } from './indicators';

/**
 * children: slide的内容
 * height?: slide的高度，外部指定，可以使数字可以是字符串
 * defaultActiveIndex: 初始化的时候激活哪个index的slide, index 从0 开始
 * duration: slides切换的时候的间隔时间
 * interval: 多久触发一次切换
 * timingFunction: 切换的动画函数
 */
interface CarouselProps {
  children: ReactNode,
  height?: number|string,
  defaultActiveIndex?: number,
  duration?: number,
  interval?: number,
  timingFunction?: TimingFunction | string
}

/**
 * 动画函数预定义枚举类型
 */
export enum TimingFunction {
  easeInOut = 'ease-in-out',
  linear = 'linear',
  ease = 'ease',
  easeIn = 'ease-in',
  easeOut = 'ease-out',
}

/**
 * 容器大小的shape
 */
interface Size {
  width: number,
  height: number,
}

/**
 * 根容器的初始化大小
 */
const initSize: Size = {
  width: 0,
  height: 0,
}

const Carousel: FC<CarouselProps> = memo(({
  children,
  height = 160,
  defaultActiveIndex = 0,
  duration=0.3,
  timingFunction=TimingFunction.easeInOut,
  interval = 3,
}) => {
  const timerRef = useRef<NodeJS.Timeout | null>(null);
  // 初始化完毕(宽度计算)，初始化渲染的flag
  const [entered, setEntered] = useState(false);
  // carousel根节点的引用
  const rootRef = useRef<HTMLDivElement>(null);
  // 获取根节点的宽度
  const rootSize = useSize(rootRef);
  // 宽度状态
  const [sizeState, setSizeState] = useState<Size>(initSize);
  // 当前激活的卡片index 从0开始
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  useEffect(() => {
    // type guide
    if (rootSize !== undefined) {
      // 初始化carousel的大小状态，同时将entered设置为true表示宽度初始化完毕，可以渲染carousel的item了
      setSizeState(rootSize);
      setEntered(true);
    }
  }, [rootSize]);

  // 算出child的个数以便计算整个slide滑动容器的总宽度
  const childCount = Children.count(children);
  // 每一个卡片的样式，包括了卡片的高度和宽度
  const carouselItemStyle = useMemo(() => ({ width: sizeState.width, height }), [height, sizeState.width]);
  // slider 滑动容器的总宽度和初始化激活哪个卡片的位置计算
  const sliderContainerStyle = useMemo(() => ({
    width:  childCount * sizeState.width,
    transitionDuration: `${duration}s`,
    transitionTimingFunction: timingFunction,
    transform: `translateX(${ -sizeState.width * (activeIndex % childCount) }px)`,
  }), [childCount, sizeState.width, activeIndex, duration, timingFunction]);

  const onSelectCarouselItem = useCallback<SelectFunction>((index) => {
    setActiveIndex(index);
  },[]);

  useEffect(() => {
    timerRef.current = global.setTimeout(() => {
      onSelectCarouselItem(activeIndex + 1)
    }, interval * 1000 );

    return () => {
      if (timerRef.current) {
        global.clearTimeout(timerRef.current);
      }
    }
  }, [activeIndex, onSelectCarouselItem, interval]);

  return (
    <div className={style.CarouseRoot} ref={rootRef}>
        {
          entered && (
            <div style={sliderContainerStyle} className={style.SlidesContainer}>
              {
                Children.map(children, (child) => (
                  <div className={style.CarouselItem} style={carouselItemStyle}>
                    {child}
                  </div>
                ))
              }
            </div>
          )
        }
        <Indicators
          activeIndex={activeIndex % childCount}
          count={childCount}
          progressDuration={interval}
          onSelect={onSelectCarouselItem}
        />
    </div>
  );
})

export default Carousel;