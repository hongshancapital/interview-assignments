import React, { useState, useEffect, useCallback, useRef } from "react";
import "./Carousel.css";

interface CarouselProps {
  list: any[];
  defaultIndex?: number;
  renderItem?: (item: any, index: number) => any;
  onChange?: (index: number) => any;
  width?: number | string;
  height?: number | string;
  duration?: number;
}

const Carousel: React.FC<CarouselProps> = (props: CarouselProps) => {
  const {
    list,
    defaultIndex = 0,
    renderItem,
    onChange,
    width = "100%",
    height = "100vh",
    duration = 3000,
  } = props;

  const [active, setActive] = useState(() => {
    if (defaultIndex > -1 && defaultIndex < list.length) {
      return defaultIndex;
    }
    throw new Error(`defaultIndex ${defaultIndex} is not exist in list`);
  });

  const onIndexChange = useCallback(
    (nextActive) => {
      setActive(nextActive);
      onChange?.(nextActive);
    },
    [onChange]
  );

  const defaultRef = useRef<number>(defaultIndex);

  useEffect(() => {
    const indicatorList = document.querySelector(".indicator-list");
    indicatorList?.classList.add("alive");
    indicatorList?.children[defaultRef.current].classList.add("active");
  }, []);

  useEffect(() => {
    const timer = setTimeout(() => {
      const next = active + 1;
      onIndexChange(next === list.length ? 0 : next);
    }, duration);

    return () => {
      clearTimeout(timer);
    };
  }, [active, duration, list.length, onIndexChange]);

  return (
    <div className="carousel" style={{ width, height }}>
      <div
        className="slide-list"
        style={{
          width: `calc(${list.length} *  ${width})`,
          left: `calc(${active} *  ${width} * -1)`,
        }}
      >
        {list.map((item, index) => {
          return (
            <div
              key={index}
              className={`slide-item ${active === index ? "active" : ""}`}
            >
              {renderItem ? renderItem(item, index) : item}
            </div>
          );
        })}
      </div>

      <div
        className="indicator-list"
        style={{ "--duration": `${duration}ms` } as React.CSSProperties}
      >
        {list.map((_, index) => {
          return (
            <button
              key={index}
              className={`indicator-item  ${active === index ? "active" : ""}`}
              onClick={() => onIndexChange(index)}
            />
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
