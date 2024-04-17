import "./carousel.css";
import React, { useState, useEffect, useRef } from "react";
import Paper from "./Paper";
import { Props } from "./ifs";
interface ifsProps extends Props {
  btnColors: string[];
  width: string | number;
  height: string | number;
  duration: number;
}
const useInterval = (callback: { (): void }, delay: number) => {
  const savedCallback = useRef<{ (): void }>();
  useEffect(() => {
    savedCallback.current = callback;
    const tick = () => {
      savedCallback.current && savedCallback.current();
    };
    let timer = setInterval(tick, delay);
    return () => clearInterval(timer);
  });
};
const Carousel = ({
  width,
  height,
  duration,
  children,
  btnColors,
}: ifsProps): JSX.Element => {
  const count = React.Children.count(children);
  let colors = [...btnColors];
  if (colors.length < count) {
    let color = "#ffffff";
    if (colors.length > 0) {
      color = colors[colors.length - 1];
      if (colors.length < count) {
        for (let i = 0; i < count - colors.length; i++) {
          colors.push(color);
        }
      }
    }else {
        colors = colors.slice(0,count)
    }
  }

  const [index, setIndex] = useState(0);
  const [direction, setDirection] = useState<"prv" | "next">("prv");
  const [btnWidth, setBtnWidth] = useState(0);
  useInterval(() => {
    if (count === 0) return;
    if (btnWidth >= 50) {
      if (direction === "next") {
        if (index < count - 1) {
          setIndex(index + 1);
        } else {
          setIndex(index - 1);
          setDirection("prv");
        }
      }
      if (direction === "prv") {
        if (index <= 0) {
          setIndex(index + 1);
          setDirection("next");
        } else {
          setIndex(index - 1);
        }
      }
      setBtnWidth(0);
    } else {
      setBtnWidth(btnWidth + 50 * (10 / duration));
    }
  }, 10);
  return (
    <div
      className="carousel"
      style={{ height: height as never, width: width as never }}
    >
      <div
        className="carousel-container"
        style={{
          height: height as never,
          width: count * 100 + "%",
          left: "-" + index * 100 + "%",
        }}
      >
        {React.Children.map(children, (child) => {
          return (
            <div className="carousel-paper" style={{ width: 100 / count + "%" }}>
              {child}
            </div>
          );
        })}
      </div>
      <div className="buttons">
        {colors.map((color, i) => {
          if (i === index && count > 0) {
            return (
              <div key={i} data-testid="btn_current"  className="button">
                <div
                  className="button-bg"
                  style={{ backgroundColor: colors[index], width: btnWidth }}
                ></div>
              </div>
            );
          }
          return (
            <div key={i} data-testid="btn_other" className="button">
              <div
                className="button-bg"
                style={{ backgroundColor: colors[index] }}
              ></div>
            </div>
          );
        })}
      </div>
      <div className="buttons buttons-bg">
        {colors.map((color, i) => {
          return (
            <div
              key={i}
              className="button"
              style={{ backgroundColor: colors[index] }}
            ></div>
          );
        })}
      </div>
    </div>
  );
};
Carousel.Paper = Paper;
export default Carousel;
