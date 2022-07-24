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
 * defaultActiveIndex: 初始化的时候激活哪个index的slide, index从0开始
 * duration: slides切换的间隔时间
 * interval: sldies过久触发一次切换
 * timingFunction: slide切换的动画函数定义
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
  /************* 组件生命周期全局变量定义 *************/
  // 自动切换timer的引用
  const timerRef = useRef<NodeJS.Timeout | null>(null);
  // carousel根节点的引用
  const rootRef = useRef<HTMLDivElement>(null);
  // 获取根节点的宽度
  const rootSize = useSize(rootRef);
  /************* 组件生命周期全局变量定义*************/


  /************* 组件状态定义 *************/
  // 初始化完毕(宽度计算)，初始化渲染的flag
  const [entered, setEntered] = useState(false);
  // 宽度状态
  const [sizeState, setSizeState] = useState<Size>(initSize);
  // 当前激活的卡片index 从0开始
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  /************* 组件状态定义 *************/


  /************* 组件衍生状态和回调函数定义 *************/
  // slides的个数
  const childCount = Children.count(children);
  // 每个slide的高度和宽度
  const carouselItemStyle = useMemo(() => ({ width: sizeState.width }), [sizeState.width]);
  // 循环激活索引
  const cyclicedActiveIndex = activeIndex % childCount;
  // slide container的样式，定义了总宽度和滑动动画
  const sliderContainerStyle = useMemo(() => ({
    height,
    width:  childCount * sizeState.width,
    transitionDuration: `${duration}s`,
    transitionTimingFunction: timingFunction,
    transform: `translateX(${ -sizeState.width * (cyclicedActiveIndex) }px)`,
  }), [childCount, sizeState.width, duration, timingFunction, cyclicedActiveIndex, height]);
  // 改变当前激活的slide的回调，用于indicator的点击，因为需要传入子组件，所以需要用useCallback包裹以便于pure render的生效
  const onSelectCarouselItem = useCallback<SelectFunction>((index) => {
    setActiveIndex(index);
  }, []);
  /************* 组件衍生状态和回调函数定义 *************/


  /************* 组件副作用定义 *************/
  // 初始化carousel的大小和首次渲染状态
  useEffect(() => {
    // ts类型守卫
    if (rootSize !== undefined) {
      setSizeState(rootSize);
      setEntered(true);
    }
  }, [rootSize]);

  // slide自动切换的副作用定义
  useEffect(() => {
    timerRef.current = global.setTimeout(() => {
      // 由于对activeIndex进行了循环取模处理, 所以这里只需要每次向切换即可
      onSelectCarouselItem(activeIndex + 1)
    }, interval * 1000 );

    return () => {
      if (timerRef.current) {
        global.clearTimeout(timerRef.current);
      }
    }
  }, [activeIndex, onSelectCarouselItem, interval]);
  /************* 组件副作用定义 *************/


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
          activeIndex={cyclicedActiveIndex}
          count={childCount}
          progressDuration={interval}
          onSelect={onSelectCarouselItem}
        />
    </div>
  );
})

export default Carousel;