import React, { useEffect, useRef, useState, useCallback } from "react";
import "./Carousel.css";
import type { CarouselItemProps, CarouseProps } from "./types";

function CarouselItem(props: CarouselItemProps) {
  const { title, text, panelStyle, goodsImgStyle } = props;
  return (
    <div className="carousel-item" style={panelStyle}>
      <div className="carousel-item-title-container">
        {Array.isArray(title) ? (
          title.map((titleItem) => {
            return (
              <div key={titleItem} className="carousel-item-title">
                {titleItem}
              </div>
            );
          })
        ) : (
          <div className="carousel-item-title">{title}</div>
        )}
      </div>
      {Array.isArray(text) ? (
        text.map((textItem) => {
          return (
            <div key={textItem} className="carousel-item-text">
              {textItem}
            </div>
          );
        })
      ) : text ? (
        <div className="carousel-item-text">{text}</div>
      ) : null}
      <div className="carousel-item-img" style={goodsImgStyle}></div>
    </div>
  );
}

export default function Carousel(props: CarouseProps) {
  const { list, interval = 3000 } = props;
  const activeRef = useRef(0);
  const [active, setActive] = useState(activeRef.current);
  const [hasAutoRun, setHasAutoRun] = useState(true);
  let timer: React.MutableRefObject<any> = useRef(null);

  const autoNext = useCallback(() => {
    timer.current = setInterval(() => {
      if (activeRef.current < list.length - 1) {
        activeRef.current = activeRef.current + 1;
      } else {
        activeRef.current = 0;
      }
      setActive(activeRef.current);
    }, interval);
  }, [list.length, interval]);

  const handleClickBar = useCallback((index: number) => {
    if (index === active) {
      return;
    }
    if (timer.current) {
      clearInterval(timer.current);
      timer.current = null;
    }
    activeRef.current = index;
    setActive(activeRef.current);
    hasAutoRun && autoNext();
  }, [active, hasAutoRun, autoNext]);

  const handleMouseEnter = () => {
    setHasAutoRun(false);
  };

  const handleMouseLeave = () => {
    setHasAutoRun(true);
  };

  useEffect(() => {
    hasAutoRun && autoNext();
    return () => {
      clearInterval(timer.current);
      timer.current = null;
    };
  }, [hasAutoRun, autoNext]);

  return (
    // 做单元测试用
    <div
      className="carousel"
      data-testid={active}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <div
        className="carousel-container"
        style={{
          width: `${list.length}00%`,
          transform: `translateX(-${active * (1 / list.length) * 100}%)`,
        }}
      >
        {list.map((item) => {
          const { key, ...rest } = item;
          return <CarouselItem key={key} {...rest} />;
        })}
      </div>
      <div className="carousel-bar">
        {list.map((item, index) => {
          return (
            <div
              key={item.key}
              className="carousel-bar-item-container"
              onClick={() => handleClickBar(index)}
            >
              <div className="carousel-bar-item">
                {active === index && (
                  <div className="carousel-bar-item-progress" style={{animationDuration: `${interval/1000}s`}}></div>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
