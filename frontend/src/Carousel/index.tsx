import React, { useEffect, useMemo, useState } from "react";
import Indicator from "./Indicator";
import "./index.css";

interface CarouselProps {
  autoplay?: boolean;
  interval?: number;
  children: React.ReactChild[];
}

function Carousel({
  autoplay = true,
  interval = 3000,
  children,
}: CarouselProps) {
  const [activeIndex, setActiveIndex] = useState(0);

  // Computed style
  const style = useMemo(
    () => ({
      transform: `translate3d(-${activeIndex * 100}vw, 0, 0)`,
    }),
    [activeIndex]
  );

  // Auto play
  useEffect(() => {
    if (!autoplay) return;
    const timer = setInterval(() => {
      setActiveIndex((activeIndex + 1) % children.length);
    }, interval);
    return () => clearInterval(timer);
  });

  return (
    <div className="carousel">
      <div className="carousel-body" style={style}>
        {children}
      </div>
      <Indicator
        activeIndex={activeIndex}
        count={children.length}
        onChange={(index) => setActiveIndex(index)}
      />
    </div>
  );
}

export default Carousel;
