import React, { useState } from "react"
import { useInterval } from "../utils";
import './Carousel.scss';

interface Option {
  delay: number;
  speed: number
}

export interface Props {
  option?: Option;
  children?: React.ReactNode;
}

export const Carousel: React.FC<Props> = (props) => {
  const { option = { delay: 2000, speed: 1000 }, children } = props;

  const childrens = React.Children.toArray(children);
  const count = React.Children.count(children);
  const [activeNum, setActiveNum] = useState(0)

  const next = () => {
    const nextNum = (activeNum + 1) % count;
    setActiveNum(nextNum)
  }

  useInterval(() => {
    next();
  }, option.delay);

  return <div className="carousel">
    <div className="carousel-items">
      {
        childrens.map((child, index) => {
          return (
            <div className="carousel-item"
              style={{
                left: `${100 * (index - activeNum)}%`,
                transition: `${option.speed}ms`
              }}
            >
              {child}
            </div>
          )
        })
      }
    </div>
    <div className="carousel-dots">
      {
        childrens.map((child, index) => {
          return <div className="carousel-dot">
            <div className="carousel-dot-process"
              style={{
                width: activeNum === index ? '100%' : '0',
                transition: activeNum === index ? `${option.delay}ms linear` : ''
              }}
            />
          </div>
        })
      }
    </div>
  </div>
}