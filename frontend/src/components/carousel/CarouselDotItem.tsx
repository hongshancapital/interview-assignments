import { useContext } from "react";
import CarouselContext from "./context";
import "./index.css";

interface CarouselDotItemProps {
  index: number;
}

function CarouselDotItem({ index }: CarouselDotItemProps) {
  const { go, activeIndex, duration } = useContext(CarouselContext);
  const isActive = activeIndex === index;
  const handleClick = () => {
    go(index);
  };

  return (
    <div
      className={`carousel-dot-item ${isActive ? "active" : ""}`}
      onClick={handleClick}
    >
      <div
        className="carousel-dot-item-progress"
        style={{
          transition: isActive ? `width ${duration}ms linear` : "none",
        }}
      ></div>
    </div>
  );
}
CarouselDotItem.displayName = "CarouselDotItem";
export default CarouselDotItem;
