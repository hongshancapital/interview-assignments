import React, { useEffect, useState, useCallback, Dispatch, SetStateAction } from "react";
import classNames from 'classnames';
import Progress from "../progress";
import "./style/index.css";
import {ICarousel} from './interface';

let autoTimer: NodeJS.Timer | null = null;

function useSlider(N: number, autoplay = true, speed = 3000): [number, Dispatch<SetStateAction<number>>] {
  // slider代表播放到第几张轮播图
  const [slider, setSlider] = useState(0);
  useEffect(() => {
    if (autoplay) {
      autoTimer = setTimeout(() => {
        setSlider((slider + 1) % N);
      }, speed);
      return () => {
        autoTimer && clearTimeout(autoTimer);
      };
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [slider]);
  return [slider, setSlider];
}

export default function Carousel(props: ICarousel) {
  const {
    autoplay,
    children,
    afterChange,
    className = ''
  }: ICarousel = props;
  const childLength = (children as React.ReactNode[])?.length || 0;
  const [slider, setSlider] = useSlider(childLength, autoplay);

  const handleClick = useCallback((index: number) => {
    if (index !== slider) {
      autoTimer && clearTimeout(autoTimer);
      setSlider(index);
      afterChange && afterChange(index);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const classes = classNames(
    'carousel',
    className
  )

  return (
    <div className={classes}>
      {/* 轮播动画部分 */}
      <div
        className="inner"
        style={{
          width: `${childLength * 100}%`,
          transform: `translateX(-${(100 * slider) / childLength}%)`,
        }}
      >
        {(children as React.ReactNode[])?.map((item, index) => (
          <span
            key={index + "carousel-part"}
            style={{
              width: `${100 / childLength}%`,
            }}
          >
            {item}
          </span>
        ))}
      </div>
      {/* 轮播按钮 */}
      <div className="carousel-bar">
        {Array(childLength)
          .fill(0)
          .map((item, index) => (
            <Progress
              key={index + "carousel-bar"}
              onClick={() => {
                handleClick(index);
              }}
              active={slider === index}
              autoplay={autoplay}
            />
          ))}
      </div>
    </div>
  );
}
