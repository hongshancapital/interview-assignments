import React, { useEffect, useRef, useState } from "react";
import './Carousel.css';

interface Props {
  interval: number;
  children: React.ReactNode[];
}

const Carousel: React.FC<Props> = (props) => {
  const { interval = 2000, children } = props;
  const length = children.length;
  const [current, setCurrent] = useState<number>(0);
  const timer = useRef<any>(null);

  const onIndicatorClick = (index: number) => {
    setCurrent(index);
    doAnimation();
  }

  const updateCurrent = () => {
    let temp;
    if (current >= length - 1) {
      temp = 0;
    } else {
      temp = current + 1;
    }
    setCurrent(temp);
    doAnimation();
  }

  const doAnimation = () => {
    document.getAnimations().forEach(item => {
      item.cancel();
      item.play();
    })
  };

  useEffect(() => {
    timer.current = setInterval(updateCurrent, interval);
    
    return () => clearInterval(timer.current);
  }, [current]);

  return (
    <div className="carousel">
      <div
        className="carousel-panel"
        style={{ transform: `translateX(-${current * 100}%)`}}
      >
        {props.children}
      </div>
      
      <div className="carousel-indicator">
        {
          props.children.map((item: any, index: number) => {
            return (
              <div
                key={index}
                className="indicator"
                onClick={ () => onIndicatorClick(index) }
              >
                <span
                  className={`${current === index ? 'active':''}`}
                  style={{ animationDuration: `${interval / 1000}s`}}
                ></span>
              </div>
            );
          })
        }
      </div>
    </div>
  );
}

export default Carousel;
