import React, { FC, useEffect, useRef, useState } from "react";

import "./styles.scss";

type DataSource = Array<{
  id: string;
  title: string;
  describe?: string;
  price?: string;
  picture: string;
  backgroundColor: string;
  color?: string;
}>;

export interface CarouselProps {
  dataSource: DataSource;
  autoplay?: boolean;
  activeIndex?: number;
}

const Carousel: FC<CarouselProps> = (props) => {
  const { dataSource, activeIndex = 0, autoplay = true } = props;

  const [width, setWidth] = useState(0);
  const [active, setActive] = useState(activeIndex);
  const refContainer = useRef<HTMLDivElement>(null);
  const timer = useRef<NodeJS.Timeout>();

  useEffect(() => {
    if (dataSource.length > 0) {
      const width = refContainer.current?.offsetWidth || 0;
      setWidth(width);

      if (autoplay) {
        timer.current = setInterval(() => {
          setActive((i) => (i === dataSource.length - 1 ? 0 : i + 1));
        }, 3000);
      }
    }

    return () => {
      timer.current && clearInterval(timer.current);
    };
  }, [dataSource, autoplay]);

  return (
    <>
      <div className="carousel" ref={refContainer}>
        <div
          className="carousel-list"
          style={{
            transform: `translate3d(-${active * width}px, 0px, 0px)`,
          }}>
          {dataSource.map((item) => {
            return (
              <div
                className="slider-item"
                key={item.id}
                style={{
                  backgroundColor: item.backgroundColor,
                  color: item.color,
                }}>
                <div className="slider-item-text">
                  <p className="slider-item-title">{item.title}</p>
                  <p className="slider-item-describe">{item.describe}</p>
                  <p className="slider-item-price">{item.price}</p>
                </div>
                <img className="slider-item-img" src={item.picture} alt="" />
              </div>
            );
          })}
        </div>
        <div className="dots">
          {dataSource.map((item, i) => {
            return (
              <div
                className="dots-item"
                key={item.id}
                onClick={() => {
                  setActive(i);
                  if (autoplay) {
                    timer.current && clearInterval(timer.current);
                    timer.current = setInterval(() => {
                      setActive((i) =>
                        i === dataSource.length - 1 ? 0 : i + 1
                      );
                    }, 3000);
                  }
                }}>
                {autoplay ? (
                  <div className={i === active ? "active" : ""}></div>
                ) : (
                  <div className={i === active ? "manual" : ""}></div>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default Carousel;
