import React, { useMemo, useState } from "react";
import Pagination from "../Pagination";
import Item from "./item";
import "./carousel.css";

export interface CarouselProps {
  datasource: { title: string; sub: string; image: string; color: string }[];
  timeout?: number;
}

const Carousel: React.FC<CarouselProps> = (props: CarouselProps) => {
  const { timeout = 2000, datasource } = props || {};
  const [activeIndex, setActiveIndex] = useState(0);

  const wrapperStyle = useMemo(() => {
    return {
      width: `${datasource?.length * 100}%`,
      transform: `translateX(${
        (activeIndex || 0) * -1 * (100 / datasource?.length)
      }%)`,
      transition: `all 0.5s ease-in`,
    };
  }, [activeIndex, datasource]);

  return (
    <>
      <div className="carousel-container">
        <div className="wrapper" style={wrapperStyle}>
          {datasource.map((item) => (
            <Item
              title={item.title}
              sub={item.sub}
              image={item.image}
              color={item.color}
            />
          ))}
        </div>
      </div>
      <Pagination
        style={{
          position: "fixed",
          bottom: "60px",
          left: 0,
          right: 0,
          margin: "auto",
        }}
        timeout={timeout}
        size={datasource?.length || 0}
        onActiveChange={(i) => setActiveIndex(i)}
      />
    </>
  );
};

export default Carousel;
