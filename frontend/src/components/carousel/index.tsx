import React, { useState, useEffect } from "react";
import "./index.css";

interface CarouselProps extends React.HTMLAttributes<HTMLElement> {
  items: CarouselItem[];
}

interface CarouselItem {
  id: number | string;
  title: string;
  text?: string;
  color: string;
  backgroundColor: string;
  pic: string;
}

function Carousel(props: CarouselProps) {
  const [current, setCurrent] = useState(-1);

  useEffect(() => {
    const intervalId = start();
    return () => stop(intervalId)
  }, []);

  function start() {
    if (current === -1) {
      setCurrent(0);
    }
    return setInterval(() => {
      setCurrent((current) => getNext(current, props.items));
    }, 3000);
  }

  function stop(intervalId: ReturnType<typeof setInterval>) {
    clearInterval(intervalId);
  }

  return (
    <div className={`Carousel ${props.className}`}>
      <div className="Carousel-items" style={getItemsStyle(current)}>
        {
          props.items.map((item) => (
            <div
              key={item.id}
              style={getItemStyle(item)}
              className="Carousel-item"
            >
              <div className="Carousel-title">{item.title}</div>
              {item.text && <div className="Carousel-text">{item.text}</div>}
            </div>
          ))
        }
      </div>
      <div className="Carousel-dots">
        {props.items.map((item, index) => (
          <div
            key={item.id}
            className={`Carousel-dot ${current === index && 'Carousel-dot--current'}`}
          />
        ))}
      </div>
    </div>
  );
}

function getItemsStyle(current: number) {
  return {
    transform: `translateX(-${current * 100}%)`,
  };
}

function getItemStyle(item: CarouselItem) {
  return {
    color: item.color,
    backgroundColor: item.backgroundColor,
    backgroundImage: `url("${item.pic}")`,
  };
}

function getNext(current: number, items: CarouselItem[]) {
  const pendingNext = current + 1;
  const shouldReset = pendingNext === items.length;
  return shouldReset ? 0 : pendingNext;
}

export default Carousel;
