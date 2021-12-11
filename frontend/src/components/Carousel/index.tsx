import React, { useEffect, useState, useRef } from "react";
import { useSize } from "../../hooks/useSize";
import { useUpdateEffect } from "../../hooks/useUpdateEffect";

import "./styles.css";

export interface DotProps {
  active?: boolean;
  duration?: number;
  strokeWidth?: number;
  strokeHeight?: number;
  strokeColor?: string;
  backgroundColor?: string;
  onClick: () => void;
}

const Dot = (props: DotProps): JSX.Element => {
  const {
    active = false,
    duration = 2000,
    strokeWidth = 50,
    strokeHeight = 2,
    strokeColor = "#fff",
    backgroundColor = "#aaa",
    onClick,
  } = props;

  const [barPercentage, setBarPercentage] = useState(0);
  const [startTimeStamp, setStartTimeStamp] = useState(0);
  const rafRef = useRef<number>();

  const animate = () => {
    const currentTimeStamp = Date.now();
    const diffTime = currentTimeStamp - startTimeStamp;

    if (diffTime < duration) {
      const percentage = (diffTime / duration) * 100;
      setBarPercentage(percentage);
      rafRef.current = requestAnimationFrame(animate);
    }
  };

  // init
  useEffect(() => {
    if (!active) {
      setBarPercentage(0);
      rafRef.current && cancelAnimationFrame(rafRef.current);
      return;
    }
    setStartTimeStamp(Date.now());
    setBarPercentage(0);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [active]);

  // animate
  useEffect(() => {
    if (startTimeStamp) {
      animate();
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [startTimeStamp]);

  return (
    <div
      className="dot"
      style={{
        backgroundColor,
        width: strokeWidth,
      }}
      onClick={onClick}
    >
      <div
        className="bar"
        style={{
          width: `${barPercentage}%`,
          backgroundColor: strokeColor,
          height: strokeHeight,
        }}
      />
    </div>
  );
};

export interface CarouselProps {
  duration?: number;
  transition?: {
    duration?: number;
    timingFunction?: string;
  };
  children: React.ReactNode;
  onChange?: (step: number) => void;
}

export const Carousel = (props: CarouselProps): JSX.Element => {
  const {
    duration = 2000,
    transition = {
      duration: 500,
      timingFunction: "linear",
    },
    children,
    onChange,
  } = props;

  const [step, setStep] = useState(0);
  const timerRef = useRef<number>(0);

  const _children = React.Children.toArray(children);
  const length = _children?.length ?? 0;

  const { ref, size } = useSize<HTMLDivElement>();

  const handleClickDot = (index: number) => {
    setStep(index);
  };

  useUpdateEffect(() => {
    onChange?.(step);
  }, [step]);

  useEffect(() => {
    timerRef.current && clearTimeout(timerRef.current);
    timerRef.current = window.setTimeout(() => {
      setStep((step) => (step + 1) % length);
    }, duration);

    return () => {
      clearTimeout(timerRef.current);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [length, step]);

  return (
    <div className="carousel" ref={ref}>
      <div
        className="content"
        style={{
          transform: `translateX(-${step * size.width}px)`,
          transition: `all ${transition.duration}ms ${transition.timingFunction}`,
        }}
      >
        {_children.map((child, index) => (
          <div
            key={index}
            className="carousel-item"
            style={{
              width: `${size.width}px`,
            }}
          >
            {child}
          </div>
        ))}
      </div>
      <div className="dots">
        {Array.from({ length }).map((_, index) => (
          <Dot
            key={index}
            duration={duration}
            active={index === step}
            onClick={() => handleClickDot(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
