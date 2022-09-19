import React, { useState, useEffect, useMemo } from "react";
import "./index.css";
import { PREFIX_CLS } from "./constans";

export interface CarouselProps {
  duration?: number;
  onChange?: () => any;
  height?: number | string;
  children: Array<React.ReactElement> | React.ReactElement;
}

export interface RefAttributes {
  active: number;
}

export const Carousel = React.forwardRef<RefAttributes, CarouselProps>(
  (
    { duration = 3000, onChange = () => {}, height = "200px", children },
    ref
  ) => {
    const [current, setCurrent] = useState<number>(0);

    const time = useMemo(
      () => ((duration % 60000) / 1000).toFixed(0),
      [duration]
    );

    React.useImperativeHandle(
      ref,
      () => {
        return { active: current };
      },
      [current]
    );

    const resetAnimation = () => {
      document
        .getAnimations?.()
        .filter((animation: any) => {
          return animation.animationName === "progressBar";
        })
        .forEach((animation) => {
          animation.cancel();
          animation.play();
        });
    };

    const handleIndicatorClick = (index: number) => {
      setCurrent(index);
      resetAnimation();
    };

    useEffect(() => {
      const timer = setTimeout(() => {
        onChange();
        if (current >= 2) {
          setCurrent(0);
        } else {
          setCurrent(current + 1);
        }
        resetAnimation();
      }, duration);
      return () => {
        if (timer) clearTimeout(timer);
      };
    }, [current, duration, onChange]);

    return (
      <div className={PREFIX_CLS} style={{ height }}>
        <div
          className={`${PREFIX_CLS}-wrapper`}
          style={{
            transform: `translateX(-${current * 100}%)`,
            transition: `1s`,
          }}
        >
          {React.Children.map(children, (child) => {
            return React.cloneElement(child as React.ReactElement, {});
          })}
        </div>
        <div className={`${PREFIX_CLS}-indicator`}>
          {React.Children.map(children, (_child, index) => {
            return (
              <React.Fragment>
                <div
                  className={`${PREFIX_CLS}-indicator_item`}
                  onClick={() => {
                    handleIndicatorClick(index);
                  }}
                >
                  <div
                    className={`${PREFIX_CLS}-indicator_inside`}
                    style={{
                      animationDuration: current === index ? `${time}s` : "0s",
                      backgroundColor: current === index ? "#fff" : "#ccc",
                    }}
                  ></div>
                </div>
              </React.Fragment>
            );
          })}
        </div>
      </div>
    );
  }
);

export default Carousel;
