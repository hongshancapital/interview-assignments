import React, { useRef } from "react";
import { useEffect, useState } from "react";
import Dots from "./Dots";
import "./index.css";

export interface CarouselProps {
  children?: React.ReactNode;
  deply?: number; // 轮播时长
  speed?: number; // 滑动速度
  width?: number; // 组件宽度
  height?: number;
}

export interface CarouselRef {
  start: () => void;
  stop: () => void;
  goTo: (index: number) => void;
  instance: any;
}

/**
 * Carousel组件
 */
const Carousel = React.forwardRef<CarouselRef, CarouselProps>((props, ref) => {
  const { width = window.innerWidth, height = window.innerHeight } = props;
  const [speed, setSpeed] = useState(props.speed || 0.5);
  const [deply] = useState(props.deply || 2000);
  // 当前展示的轮播图
  const [curIndex, setCurIndex] = useState(0);
  // 轮播的子元素数量
  const count = React.useRef(React.Children.count(props.children)).current;
  const carouselRef = React.useRef<any>();
  const indexRef = useRef(0);
  const timerRef = useRef<NodeJS.Timeout | null>(null);
  // 开始轮播
  const start = () => {
    timerRef.current = setInterval(() => {
      if (indexRef.current >= count) {
        indexRef.current = 0;
        setSpeed(0);
        setCurIndex(indexRef.current);
        // 滑到最后一张的时候，无感切到第一张
        setTimeout(() => {
          setSpeed(speed);
          indexRef.current = 1;
          setCurIndex(indexRef.current);
        }, 30);
      } else {
        indexRef.current++;
        setSpeed(speed);
        setCurIndex(indexRef.current);
      }
    }, deply);
  };

  // 暂停轮播
  const stop = () => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }
  };

  // 跳转到指定的子元素
  const goTo = (index: number) => {
    setCurIndex(index);
  };

  React.useImperativeHandle(
    ref,
    () => ({
      goTo,
      start,
      stop,
      instance: { curIndex, deply },
    }),
    [carouselRef.current]
  );

  useEffect(() => {
    if (count > 0) {
      start();
    } else {
      if (process.env.NODE_ENV !== "production") {
        console.warn("carousel组件没有可显示的内容");
      }
    }
    return () => {
      stop();
    };
  }, [props.children]);

  const childrens = () => {
    return React.Children.map(props.children, (value, index) => {
      return (
        <div className="item" key={index} style={{ width: `${width}px` }}>
          {value}
        </div>
      );
    });
  };

  return (
    <div
      ref={carouselRef}
      className="carousel"
      style={{ width: `${width}px`, height: `${height}px` }}
    >
      <div
        className="wrap"
        style={{
          width: `${(count + 1) * width}px`,
          left: `-${curIndex * width}px`,
          transition: `left ${speed}s linear`,
        }}
      >
        {childrens()}
        {childrens()?.[0]}
      </div>
      <Dots count={count} selectIndex={curIndex} deply={deply} />
    </div>
  );
});

export default Carousel;
