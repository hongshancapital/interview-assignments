import React, {
  FC,
  useState,
  ReactNode,
  useCallback,
  useMemo,
} from "react";
import classnames from "classnames";
import { ICarouselProps } from "./type";
import "./index.css";
import { useTimer } from "./hooks";

const Carousel: FC<ICarouselProps> = (props) => {
  const {
    className = "",
    style = {},
    duration = 2000,
    autoPlay = true,
    children = [],
    animationDuration = 2000,
  } = props;
  const [activeIndex, setActiveIndex] = useState<number>(0);
  const childrenLen = useMemo(() => children.length, [children]);

  const [restartTimer] = useTimer(() => {
    setActiveIndex((last) => {
      const current = last + 1;
      return current >= childrenLen ? 0 : current;
    });
  }, duration, autoPlay);

  const goTo = useCallback(
    (index: number) => {
      if (index === activeIndex) return;
      setActiveIndex(index);
      restartTimer();
    },
    [activeIndex, restartTimer]
  );

  const transformStyle = useMemo(() => {
    return {
      transform: `translateX(${(-activeIndex / childrenLen) * 100}%)`,
      transitionDuration: `${animationDuration / 1000}s`,
      width: `${childrenLen * 100}%`,
    };
  }, [activeIndex, childrenLen, animationDuration]);

  return (
    <div className={classnames("carousel", className)} style={{ ...style }}>
      <div className="carousel-slider" style={transformStyle} >
        {(children || []).map((item: ReactNode, index: number) => (
          <div key={index} className="carousel-slide">
            {item}
          </div>
        ))}
      </div>
      <ul className="carousel-dots">
        {(children || []).map((_item: ReactNode, index: number) => (
          <li key={index} onClick={() => goTo(index)}>
            {activeIndex === index && (
              <span
                className="carousel-dots-highlight"
                style={{
                  animationDuration: `${animationDuration / 1000}s`,
                }}
              />
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Carousel;
