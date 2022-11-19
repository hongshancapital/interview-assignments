import React from "react";
import clsx from "clsx";
import useIndicator from "./useIndicator";
import "./index.scss";

interface ICarouselProps {
  customClass?: string;
  customStyle?: React.CSSProperties;
  itemTransitionDuration?: number;
  indicatorAnimationDuration?: number;
  data: any[];
  children: (item: any) => React.ReactNode;
}

const Carousel: React.FC<ICarouselProps> = ({
  customClass,
  customStyle,
  data,
  itemTransitionDuration = 1000,
  indicatorAnimationDuration = 2000,
  children,
}) => {
  const itemStyle = {
    width: customStyle?.width ?? "100px",
    height: customStyle?.height ?? "100px",
  };

  const [activeIndex, handleAnimationEnd] = useIndicator(data.length);

  return (
    <div
      className={clsx("interview-carousel", customClass)}
      style={{ ...customStyle, ...itemStyle }}
    >
      <div
        className="item-container"
        style={{
          transform: `translateX(-${activeIndex * 100}%)`,
          transitionDuration: `${Math.min(
            itemTransitionDuration,
            indicatorAnimationDuration
          )}ms`,
        }}
      >
        {data.map((item) => (
          <div className="item" style={itemStyle}>
            {children(item)}
          </div>
        ))}
      </div>
      <div className="indicator-container">
        {data.map((_, index) => (
          <div key={index} className="indicator">
            <div
              className={clsx("mark", { active: activeIndex === index })}
              onAnimationEnd={handleAnimationEnd}
              style={{ animationDuration: `${indicatorAnimationDuration}ms` }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
