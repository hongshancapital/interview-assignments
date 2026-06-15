import React, { useEffect, useState, useRef } from "react";
import { ICarouseProps } from "./types";
import styles from "./index.module.css";
import CarouselItem from "./CarouselItem";

const Carousel: React.FC<ICarouseProps> = (props) => {
  const { children = [], showIndicator = true, duration = 3, boxStyle } = props;

  const [activeIndex, setActiveIndex] = useState(0);
  const intervalId: React.MutableRefObject<NodeJS.Timer | null> = useRef(null);

  useEffect(() => {
    intervalId.current && clearTimeout(intervalId.current);
    intervalId.current = setTimeout(() => {
      if (activeIndex < children.length - 1) {
        setActiveIndex(activeIndex + 1);
      } else {
        setActiveIndex(0);
      }
    }, duration * 1000);
    return () => {
      intervalId.current && clearTimeout(intervalId.current);
    };
  }, [activeIndex, children, duration]);

  const manualSwitch = (index: number) => {
    setActiveIndex(index);
  };

  return (
    <ul className={styles["carousel-box"]} style={boxStyle}>
      {children.map((item, index) => {
        return (
          <li
            className={styles["img-box"]}
            key={index}
            style={{
              transform: `translateX(-${activeIndex * 100}%)`,
            }}
          >
            {item}
          </li>
        );
      })}
      {showIndicator && children.length && (
        <div className={styles["indicator-wrap"]}>
          {children.map((item, index) => {
            return (
              <div
                className={styles["indicator-item"]}
                key={index}
                onClick={() => manualSwitch(index)}
              >
                {activeIndex === index ? (
                  <div
                    className={styles["indicator-animation"]}
                    style={{ animationDuration: `${duration}s` }}
                  ></div>
                ) : null}
              </div>
            );
          })}
        </div>
      )}
    </ul>
  );
};

export default Carousel;
export { CarouselItem };
