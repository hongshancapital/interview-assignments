import React, { useEffect, useState } from "react";
import './index.css'

interface Props {
  children: React.ReactNode
}

export const CarouselItem:React.FC<Props> = ({ children }) => {
  return <div className="carousel">{children}</div>;
};

export const Carousel:React.FC<Props> = ({ children }) => {
  const [activeIndex, setActiveIndex] = useState<number>(0);

  const updateIndex = (index:number) => {
    const totalCount = React.Children.count(children);
    if (index < 0) {
      index = totalCount - 1;
    } else if (index >= totalCount) {
      index = 0;
    }
    setActiveIndex(index);
  };

  useEffect(() => {
    let id = setInterval(() => {
      updateIndex(activeIndex + 1);
    }, 4000);

    return () => {
      clearInterval(id);
    };
  });

  return (
    <div className="CarouselWrapper">
      <div
        className="inner"
        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
      >
        {children}
      </div>

      <div className="footer">
        {React.Children.toArray(children).map((_: any, index:number) => {
          return (
            <div
              key={index}
              onClick={() => updateIndex(index)}
              style={{
                position: "relative",
                display: "inline-block",
                width: "40px",
                height: "2px",
                background: "#a9a9a9",
                marginRight: '5px',
              }}>
              <span
              className={`${activeIndex === index ? "active" : ""}`}
              >
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
};
