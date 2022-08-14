import React, {
  FC,
  forwardRef,
  memo,
  PropsWithChildren,
  RefAttributes,
  useEffect,
  useImperativeHandle,
  useMemo,
  useRef,
  useState,
} from 'react';
import { useUpdate } from '../../common/common';
import styles from './Carousel.module.css';
import { CarouselItem } from './CarouselItem';
import { Indicator } from './Indicator';

export interface CarouselProps {
  autoplay?: boolean;
  /** 当前页面持续时间，单位:ms，默认: 4000 */
  duration?: number;
  /** 是否使用指示器, 默认底部指示器 */
  indicator?: boolean;
  /** 指示器的宽度，默认: 40 */
  indicatorWidth?: number;
  current?: number;
  /** 当前元素变更时候，变更事件 */
  onChange?: (current: number) => void;
}

export interface CarouselRefMethod {
  play: () => void;
  pause: () => void;
  update: (index: number) => void;
}

const MemoCarousel: FC<CarouselProps> = memo<PropsWithChildren<CarouselProps>>(
  forwardRef<CarouselRefMethod, CarouselProps>(
    (
      { duration = 4000, autoplay = true, indicator = true, current = 0, indicatorWidth = 40, children, onChange },
      ref
    ) => {
      // 当前所显示子元素的索引
      const [inCurrent, setInCurrent] = useState(current);
      const [playing, setPlaying] = useState(autoplay);

      const childrenArray = React.Children.toArray(children);

      // 获取跑马灯子元素
      const items = childrenArray
        .filter((c: any) => c.type === CarouselItem)
        .map((c: any, i) => React.cloneElement(c, { style: { transform: `translate3d(${i * 100}%, 0, 0)` } }));

      // 用于处理当前跑马灯动画
      const innerStyle = useMemo(() => {
        return { transform: `translate3d(${-inCurrent * 100}%, 0, 0)` };
      }, [inCurrent]);

      useEffect(() => {
        setInCurrent(current);
      }, [current]);

      useUpdate(() => {
        onChange?.(inCurrent);
      }, [inCurrent, onChange]);

      const timer = useRef<NodeJS.Timer | null>(null);
      const play = () => {
        setPlaying(true);
        timer.current = setInterval(() => {
          setInCurrent((prev) => {
            if (prev >= items.length - 1) {
              return 0;
            }
            return prev + 1;
          });
        }, duration);
      };

      const pause = () => {
        setPlaying(false);
        timer.current && clearInterval(timer.current);
      };

      useImperativeHandle(
        ref,
        () => {
          return { pause, play, update: (index: number) => setInCurrent(index) };
        },
        []
      );

      useEffect(() => {
        if (autoplay) {
          play();
          return pause;
        }
      }, [autoplay]);

      return (
        <div className={styles.carousel}>
          <div className={styles.carouselInner} style={innerStyle}>
            {items}
          </div>
          {indicator && (
            <div className={styles.indicatorOuter} key={items.length}>
              {items.map((_, i) => (
                <Indicator duration={duration} key={i} active={i === inCurrent && playing} width={indicatorWidth} />
              ))}
            </div>
          )}
        </div>
      );
    }
  )
);

export const Carousel = MemoCarousel as FC<CarouselProps & RefAttributes<CarouselRefMethod>> & {
  Item: typeof CarouselItem;
};

Carousel.Item = CarouselItem;
Carousel.displayName = 'Carousel';
