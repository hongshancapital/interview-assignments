import React, { useState, useEffect, useRef } from "react";
import "./index.css";
import Dots from "./dots";

export interface MyCarouselProps {
  children?: React.ReactNode;
  width?: number;
  height?: number;
  dots?: boolean;
  delay?: number;
}

const MyCarousel: React.FC<MyCarouselProps> = ({
  children,
  width = 800,
  height = 450,
  dots = true,
  delay = 3000,
}) => {
  const [activeKey, setActiveKey] = useState<number>(0);
  const myCarouselTimer = useRef<NodeJS.Timer>();
  const moveDomNode = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    copyElement();
    goTo();
    return () => {
      myCarouselTimer.current && clearInterval(myCarouselTimer.current);
    };
  }, []);

  useEffect(() => {
    if (activeKey === React.Children.count(children)) {
      setActiveKey(0);
    }
  }, [activeKey]);

  const copyElement = () => {
    const copyFirstElement =
      moveDomNode.current &&
      moveDomNode.current.childNodes &&
      (moveDomNode.current.childNodes[0].cloneNode(true) as HTMLDivElement);
    if (moveDomNode.current && copyFirstElement) {
      moveDomNode.current.appendChild(copyFirstElement);
    }
  };

  const move = (index: number) => {
    if (moveDomNode.current) {
      setActiveKey(index);
      moveDomNode.current.style.transform = `translateX(-${index * width}px)`;
      moveDomNode.current.style.transition = !index ? "none" : "all 1s";
    }
  };

  const goTo = () => {
    const count: number = React.Children.count(children);
    let index: number = 0;
    myCarouselTimer.current && clearInterval(myCarouselTimer.current);
    myCarouselTimer.current = setInterval(() => {
      if (index < count) {
        index++;
        move(index);
        if (index === count) {
          index = 0;
          move(0);
        }
      }
    }, delay);
  };

  const renderChildren = (): React.ReactElement => {
    return (
      <div className="myCarousel-list" ref={moveDomNode}>
        {React.Children.map(children, (child, i) => {
          return (
            <div
              className="myCarousel-item"
              style={{ flex: `0 0 ${width}px`, height: `${height}px` }}
              key={i}
            >
              {child}
            </div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="myCarousel-container" style={{ width: `${width}px`, height: `${height}px` }}>
      {renderChildren()}
      {dots && <Dots children={children} delay={delay} activeKey={activeKey} />}
    </div>
  );
};

export default MyCarousel;
