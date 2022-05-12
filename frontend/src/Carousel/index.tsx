import React from "react";
import Indicator from "./Indicator";
import { useAutoPlay } from "./hooks/useAutoplay";
import "./index.css";

export interface CarouselProps {
  autoPlay?: boolean;
  interval?: number;
  children?: React.ReactChild[];
}

const Carousel = ({
  autoPlay = true,
  interval = 3000,
  children = [],
}: CarouselProps) => {
  const { activeIndex, setActiveIndex } = useAutoPlay({
    count: children.length,
    interval,
    autoPlay,
  });

  return (
    <div className="carousel">
      <div className={`carousel-body carousel-body-${activeIndex}`}>
        {children}
      </div>
      <Indicator
        activeIndex={activeIndex}
        count={children.length}
        onChange={(index) => setActiveIndex(index)}
      />
    </div>
  );
};

export default Carousel;
