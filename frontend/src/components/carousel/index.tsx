import React, { useState, useEffect, useMemo } from "react";
import "./index.css";
import { PREFIX_CLS } from "./constans";

interface CarouselProps {
  data: Array<{
    id?: string | number;
    title?: string | React.ReactNode;
    description?: string | React.ReactNode;
    image?: string;
    backgroundColor?: string;
    color?: string;
  }>;
  duration?: number;
  onChange?: () => any;
  height?: number | string;
}

export const Carousel = React.forwardRef<{ slickNum: number }, CarouselProps>(
  (
    { duration = 3000, onChange = () => {}, data = [], height = "200px" },
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
        return { slickNum: current };
      },
      [current]
    );

    useEffect(() => {
      const timer = setTimeout(() => {
        onChange();
        if (current >= 2) {
          setCurrent(0);
        } else {
          setCurrent(current + 1);
        }
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
            backgroundColor: data[current]?.backgroundColor,
            transform: `translateX(-${current * 100}%)`,
            transition: `${duration / 1000}s`,
          }}
        >
          {data.map((item, i) => {
            const {
              backgroundColor = "#fff",
              color = "#000",
              image = "",
              title = "",
              description = "",
            } = item;
            return (
              <div
                className={`${PREFIX_CLS}-item`}
                key={item.id || i}
                style={{
                  backgroundColor,
                  color,
                }}
              >
                <div className={`${PREFIX_CLS}-item_info`}>
                  <div className={`${PREFIX_CLS}-item_title`}>{title}</div>
                  <div className={`${PREFIX_CLS}-item_description`}>
                    {description}
                  </div>
                </div>
                <div className={`${PREFIX_CLS}-item_image`}>
                  <img src={image} alt='图片' />
                </div>
              </div>
            );
          })}
        </div>
        <div className={`${PREFIX_CLS}-indicator`}>
          {data.map((item, index) => {
            return (
              <React.Fragment key={item.id || index}>
                <div
                  className={`${PREFIX_CLS}-indicator_item`}
                  onClick={() => {
                    setCurrent(index);
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
