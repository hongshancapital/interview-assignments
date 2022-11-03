import React, { useEffect, useRef, useState, useCallback } from "react";
import "./Carousel.css";
import type { CarouselItemProps, CarouseProps, CarouseBarProps } from "./types";

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

function CarouselBarItem(props: CarouseBarProps) {
  const { currentIndex, isActive, interval, onClick } = props;
  return (
    <div
      className="carousel-bar-item-container"
      onClick={() => onClick(currentIndex)}
    >
      <div className="carousel-bar-item">
        {isActive && (
          <div
            className="carousel-bar-item-progress"
            style={{ animationDuration: `${interval / 1000}s` }}
          ></div>
        )}
      </div>
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

  const handleClickBar = useCallback(
    (index: number) => {
      if (index === active) {
        return;
      }
      if (timer.current) {
        clearInterval(timer.current);
        timer.current = null;
      }
      activeRef.current = index;
      setActive(activeRef.current);
    },
    [active]
  );

  const handleMouseEnter = useCallback(() => {
    setHasAutoRun(false);
  }, [setHasAutoRun]);

  const handleMouseLeave = useCallback(() => {
    setHasAutoRun(true);
  }, [setHasAutoRun]);

  useEffect(() => {
    hasAutoRun && autoNext();
    return () => {
      clearInterval(timer.current);
      timer.current = null;
    };
  }, [hasAutoRun, autoNext]);

  return (
    <div
      className="carousel"
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
            <CarouselBarItem
              key={item.key}
              currentIndex={index}
              isActive={active === index}
              interval={interval}
              onClick={handleClickBar}
            />
          );
        })}
      </div>
    </div>
  );
}
