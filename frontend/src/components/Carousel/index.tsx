import React from "react";
import { useActiveIndex } from "./helper";
import { ICarouselItem } from "../../type/carousel";
import CarouselItem from "../CarouseItem";
import Progress from "../Progress";
import "./index.css";

const Index = ({ data = [] }: { data: ICarouselItem[] }) => {
  const activeIndex = useActiveIndex(data.length);
  const transform = React.useMemo(() => {
    return `translateX(${-activeIndex * 100}%)`;
  }, [activeIndex]);
  return (
    <div className="content">
      <div className="carousel_wrapper" style={{ transform: transform }}>
        {data.length &&
          data.map((item) => <CarouselItem item={item} key={item.id} />)}
      </div>
      {data.length && (
        <Progress count={data.length} activeIndex={activeIndex} ms={3000} />
      )}
    </div>
  );
};

export default Index;
