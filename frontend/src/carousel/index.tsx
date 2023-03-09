import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import styled from "styled-components";
import { Dots } from "./Dots";

export interface ICarouseItem {
  element: React.ReactElement;
  id: string;
}
export interface ICarouselProps {
  list: ICarouseItem[];
  // 展示时长, 单位 ms
  duration?: number;
  // 动画时长，单位 ms
  speed?: number;
}

const StyledCarousel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;

  .box {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: nowrap;

    .item {
      width: 100%;
      height: 100%;
      flex-shrink: 0;
    }
  }
`;

const NEXT_TICK_TIME = 20;

export interface CarouselRef {
  instance: {
    list: ICarouseItem[];
    speed: number;
    duration: number;
  };
}

export const Carousel = React.forwardRef<CarouselRef, ICarouselProps>(
  (props, ref) => {
    const { list, duration = 2000 } = props;
    const carouselRef = React.useRef<any>();
    // 当前轮播下标
    const [currentIndex, setCurrentIndex] = useState(0);
    const indexRef = useRef<number>(0);
    // 数量
    const count = useMemo(() => list.length, [list]);
    const [speed, setSpeed] = useState(props.speed || 1);
    const timer = useRef<ReturnType<typeof setTimeout> | null>(null);

    const start = useCallback(() => {
      timer.current = setInterval(() => {
        if (indexRef.current < count) {
          indexRef.current++;
          setCurrentIndex(indexRef.current);
          return;
        }

        // 如果在给定的最后一张（但因为末尾增加了第 0 张，所以这里是倒数第二张
        // 停掉 left 移动的动画
        setSpeed(0);
        indexRef.current = 0;
        setCurrentIndex(indexRef.current);

        setTimeout(() => {
          // 恢复动画
          setSpeed(props.speed || 1);
          indexRef.current = 1;
          setCurrentIndex(indexRef.current);
        }, NEXT_TICK_TIME);
      }, duration);
    }, [count]);

    const stop = useCallback(() => {
      if (!timer.current) {
        return;
      }
      clearInterval(timer.current);
    }, []);

    React.useImperativeHandle(
      ref,
      () => ({
        instance: { list, duration, speed },
      }),
      [carouselRef.current]
    );

    useEffect(() => {
      // 大于 1 才轮播
      if (count > 1) {
        start();
      }
      return () => {
        stop();
      };
    }, [start, stop, count]);

    return (
      <StyledCarousel ref={carouselRef}>
        <div
          className="box"
          style={{
            left: `-${currentIndex * 100}%`,
            transition: `left ${speed}s linear`,
          }}
        >
          {list.map((item) => (
            <div className="item" key={item.id}>
              {item.element}
            </div>
          ))}
          {list[0] ? <div className="item">{list[0]?.element}</div> : null}
        </div>
        <Dots count={count} selectedIndex={currentIndex} duration={duration} />
      </StyledCarousel>
    );
  }
);
